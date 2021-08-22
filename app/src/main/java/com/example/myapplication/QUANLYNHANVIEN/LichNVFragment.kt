package com.example.myapplication.QUANLYNHANVIEN
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.QUANLYHUYMON.APIService
import com.example.myapplication.QUANLYHUYMON.ServiceGenerator
import com.example.myapplication.R
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_lich_n_v.*
import kotlinx.android.synthetic.main.fragment_lich_n_v.view.*
import kotlinx.android.synthetic.main.lich_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class LichNVFragment : Fragment(){
    var options = arrayListOf<String>()
    var startdate:String = ""
    var enddate:String = ""
    var starttime:String=""
    var endtime:String=""
    var diemtru = 0
    var LSnhanvienList = mutableListOf<lichsunhanvienitem>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_lich_n_v, container, false)
        view.chondate.setOnClickListener {
            val dateRangePicker = MaterialDatePicker.Builder
                .dateRangePicker()
                .setTitleText("Chọn ngày")
                .build()
            dateRangePicker.show(childFragmentManager, "OK")
            dateRangePicker.addOnPositiveButtonClickListener { date ->
                startdate = convertLongToTime(date.first)
                startDate.setText(startdate)
                enddate = convertLongToTime(date.second)
                endDate.setText(enddate)
            }
        }
        initautocomlete(view)
        initdatepicker(view,view.startDate)
        inittimepicker(view,view.startTime,"5:00")
        initdatepicker(view,view.endDate)
        inittimepicker(view,view.endTime)
        view.rev_lichsunhanvien.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = LichSuNVAdapter(LSnhanvienList)
            }
        click_btn_xem(view)
        view.btn_xacnhan.setOnClickListener { click_btn_xacnhan(view) }
        view.btn_xem.setOnClickListener { click_btn_xem(view) }
        return view
    }

    private fun click_btn_xacnhan(view:View) {
        if (LSnhanvienList.isNotEmpty()) {
            val gson = Gson()
            val data = gson.toJson(LSnhanvienList)
            startdate=view.startDate.text.toString()
            enddate = view.endDate.text.toString()
            starttime = view.startTime.text.toString()
            endtime = view.endTime.text.toString()
            diemtru = view.edt_diemtru.text.toString().toInt()
            val tennhanvien = view.autoCompletenhanvien.text.toString()
            Log.e("TEST","?TENNV=$tennhanvien&startDate=$startdate&endDate=$enddate&startTime=$starttime&endTime=$endtime&diemtru=$diemtru&data=$data")
            danhsachlichsunhanvien(view,tennhanvien,startdate,enddate,starttime,endtime,diemtru,data.toString())
        }else{
            Toast.makeText(activity, "Chưa có danh sách chọn nào!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun click_btn_xem(view: View) {
        startdate=view.startDate.text.toString()
        enddate = view.endDate.text.toString()
        starttime = view.startTime.text.toString()
        endtime = view.endTime.text.toString()
        val tennhanvien = view.autoCompletenhanvien.text.toString()
            danhsachlichsunhanvien(view,tennhanvien,startdate,enddate,starttime,endtime)
    }
    private fun convertLongToTime(date:Long):String {
        val day = Date(date)
        val format = SimpleDateFormat("yyyy-MM-dd",Locale.getDefault())
        return format.format(day)
    }
    private fun initdatepicker(view:View, datePicker: EditText) {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        var date:Date = Calendar.getInstance().time
        val today = formatter.format(date)
        datePicker?.setText(today)
    }
    private fun inittimepicker(view:View, timePicker: EditText,time:String?="") {
        timePicker?.setText(time)
        if (time=="") {
            val formatter = SimpleDateFormat("H:mm")
            var date: Date = Calendar.getInstance().time
            val today = formatter.format(date)
            timePicker?.setText(today)
        }
    }
    private fun initautocomlete(view: View) {
        danhsach()
        val autocompletenhanvien= view?.findViewById<AutoCompleteTextView>(R.id.autoCompletenhanvien)
        var adapter = activity?.let { ArrayAdapter(it,android.R.layout.simple_list_item_1,options) }
        autocompletenhanvien?.threshold=0
        autocompletenhanvien?.setAdapter(adapter)
    }
    private fun danhsach() {
        val serviceGenerator = ServiceGenerator.buildService(APIService::class.java)
        val call = serviceGenerator.danhsachnhanvien()
        call.enqueue(object : Callback<MutableList<danhsachmodel>> {
            override fun onResponse(
                call: Call<MutableList<danhsachmodel>>,
                response: Response<MutableList<danhsachmodel>>
            ) {
                if (response.isSuccessful) {
                    updateDS(response.body())
                }
            }
            override fun onFailure(call: Call<MutableList<danhsachmodel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("SANPHAM3", t.message.toString())
            }
        })
    }
    private fun danhsachlichsunhanvien(
        view: View,
        TENNV: String,
        startDate: String? = "",
        endDate: String? = "",
        startTime: String? = "",
        endTime: String? = "",
        diemtru:Int?=0,
        data:String?=""
    ) {
        val serviceGenerator = ServiceGenerator.buildService(APIService::class.java)
        val call = serviceGenerator.lichsunhanvien(TENNV,startDate,endDate,startTime,endTime,diemtru,data)
        call.enqueue(object : Callback<MutableList<lichsunhanvienitem>> {
            override fun onResponse(
                call: Call<MutableList<lichsunhanvienitem>>,
                response: Response<MutableList<lichsunhanvienitem>>
            ) {
                if (response.isSuccessful) {
                    view.layout_trudiem.visibility = View.GONE
                    updateList(response.body(),view)
                }
            }
            override fun onFailure(call: Call<MutableList<lichsunhanvienitem>>, t: Throwable) {
                t.printStackTrace()
                Log.e("SANPHAM3", t.message.toString())
            }
        })
    }

    private fun updateList(body: MutableList<lichsunhanvienitem>?, view: View) {
        LSnhanvienList.clear()
        LSnhanvienList.addAll(body!!)
        view.rev_lichsunhanvien.adapter?.notifyDataSetChanged()
    }

    private fun updateDS(body: MutableList<danhsachmodel>?) {
        options.clear()
        for (i in 0 until body!!.size){
            options.add(body[i].TENNV)
        }
    }
}
