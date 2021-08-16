package com.example.myapplication.QUANLYNHANVIEN
import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.util.Pair
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.example.myapplication.QUANLYHUYMON.APIService
import com.example.myapplication.QUANLYHUYMON.ServiceGenerator
import com.example.myapplication.R
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_lich_n_v.*
import kotlinx.android.synthetic.main.fragment_lich_n_v.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class LichNVFragment : Fragment(){
    var options = arrayListOf<String>()
    var startdate:String = ""
    var enddate:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =inflater.inflate(R.layout.fragment_lich_n_v, container, false)
        view.btn_xem.setOnClickListener {
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
        initdatepicker(view,view.endDate)
        return view
    }

    private fun convertLongToTime(date:Long):String {
        val day = Date(date)
        val format = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
        return format.format(day)
    }


    private fun initdatepicker(view:View, datePicker: EditText) {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        var date:Date = Calendar.getInstance().time
        val today = formatter.format(date)
        datePicker?.setText(today)
        val day = Calendar.getInstance()
        val year = day.get(Calendar.YEAR)
        val month = day.get(Calendar.MONTH)
        val dayofmonth = day.get(Calendar.DAY_OF_MONTH)
        val dpd=
            activity?.let {
                DatePickerDialog(it,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                    datePicker?.setText(""+dayOfMonth+"/"+month+"/"+year)
                },year,month,dayofmonth)
            }
        datePicker?.setOnClickListener {
            dpd?.show()
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

    private fun updateDS(body: MutableList<danhsachmodel>?) {
        options.clear()
        for (i in 0 until body!!.size){
            options.add(body[i].TENNV)
        }
    }
}
