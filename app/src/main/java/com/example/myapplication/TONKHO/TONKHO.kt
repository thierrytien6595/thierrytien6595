package com.example.myapplication.TONKHO

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.QUANLYHUYMON.APIService
import com.example.myapplication.QUANLYHUYMON.ServiceGenerator
import com.example.myapplication.R
import com.example.myapplication.variable.BIEN
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_tonkho.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TONKHO : AppCompatActivity(), SPAdapter.OnItemClickListener, ThemAdapter.OnItemClickListener {

    val SPNhapList = mutableListOf<SanPhamModel>()
    val SPList = mutableListOf<SanPhamModel>()
    val NhapList = mutableListOf<SanPhamModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tonkho)
        rev_chonmon2.visibility = View.GONE
        rev_chonmon2.apply {
            layoutManager = LinearLayoutManager(this@TONKHO)
            adapter = ThemAdapter(SPNhapList,this@TONKHO)
        }
        rev_chonmon.apply {
            layoutManager = LinearLayoutManager(this@TONKHO)
            adapter = SPAdapter(SPList,this@TONKHO)
        }
        capnhatSP()
    }

    private fun capnhatSP() {
        val serviceGenerator = ServiceGenerator.buildService(APIService::class.java)
        val call = serviceGenerator.getSP("quanly")
        call.enqueue(object : Callback<MutableList<SanPhamModel>> {
            override fun onResponse(
                call: Call<MutableList<SanPhamModel>>,
                response: Response<MutableList<SanPhamModel>>
            ) {
                if (response.isSuccessful) {
                        updatedata(response.body())
                }
            }

            override fun onFailure(call: Call<MutableList<SanPhamModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("SANPHAM3", t.message.toString())
            }
        })
    }

    private fun updatedata(body: MutableList<SanPhamModel>?) {
        SPList.clear()
        SPNhapList.clear()
        rev_chonmon2.adapter?.notifyDataSetChanged()
        SPList.addAll(body!!)
        rev_chonmon.adapter?.notifyDataSetChanged()
    }

    fun onClick(v: View?) {
        when(v?.id){
            btn_nhapkho.id -> {
                rev_chonmon2.adapter?.notifyDataSetChanged()
                for (i in 0 until SPNhapList.size){
                    val index = NhapList.lastIndexOf(NhapList.findLast {it.MASP==SPNhapList[i].MASP})
                    if (index==-1) // Nếu SP chọn chưa có trong list
                    {
                        NhapList.add(SanPhamModel(SPNhapList[i].TENSP, SPNhapList[i].HINHSP,SPNhapList[i].GIASP,SPNhapList[i].MASP,SPNhapList[i].SOLUONG))
                    }
                    else{// Nếu có rồi thì tăng số lượng lên
                        NhapList.set(index, SanPhamModel(SPNhapList[i].TENSP, SPNhapList[i].HINHSP,SPNhapList[i].GIASP,SPNhapList[i].MASP,NhapList[index].SOLUONG+SPNhapList[i].SOLUONG))
                    }
                }
                sentData(SPNhapList)
            }
        }
    }
    override fun onItemClick(data: SanPhamModel) {
        val index = SPNhapList.lastIndexOf(SPNhapList.findLast {it.TENSP==data.TENSP})
        if (index==-1) // Nếu SP chọn chưa có trong list
        {
            SPNhapList.add(SanPhamModel(data.TENSP, data.HINHSP,data.GIASP,data.MASP,0))
        }
        else{// Nếu có rồi thì tăng số lượng lên
            SPNhapList.removeAt(index)
        }
        if (SPNhapList != emptyList<SanPhamModel>()) {
            rev_chonmon2.visibility=View.VISIBLE
            rev_chonmon2.adapter?.notifyDataSetChanged()
        }
        else {
            rev_chonmon2.visibility=View.GONE
        }
    }

    override fun onClickTextView(data: SanPhamModel) {
        val index = SPNhapList.lastIndexOf(SPNhapList.findLast {it.MASP==data.MASP})
        if(index!=-1) {
            SPNhapList.set(index, SanPhamModel(data.TENSP, data.HINHSP,data.GIASP,data.MASP, SPNhapList[index].SOLUONG + 10))
            rev_chonmon2.adapter?.notifyDataSetChanged()
        }
    }

    override fun onClickAddBtn(data: SanPhamModel) {
        val index = SPNhapList.lastIndexOf(SPNhapList.findLast {it.MASP==data.MASP})
        if(index!=-1) {
            SPNhapList.set(index, SanPhamModel(data.TENSP, data.HINHSP,data.GIASP,data.MASP, SPNhapList[index].SOLUONG + 1))
            rev_chonmon2.adapter?.notifyDataSetChanged()
        }
    }

    override fun onClickRemoveBtn(data: SanPhamModel) {
        val index = SPNhapList.lastIndexOf(SPNhapList.findLast {it.MASP==data.MASP})
        if(index!=-1) {

            if (SPNhapList[index].SOLUONG!=0){
                SPNhapList.set(index, SanPhamModel(data.TENSP, data.HINHSP,data.GIASP,data.MASP, SPNhapList[index].SOLUONG - 1))
            }
            rev_chonmon2.adapter?.notifyDataSetChanged()
        }
    }

    private fun sentData(data: MutableList<SanPhamModel>){
        val gson = Gson()
        val data = gson.toJson(data)
        val myurl = BIEN().local+"nhaphang.php?data=$data"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, myurl,
            {
                Log.e("NHAPSP","sent OK! $myurl")
                capnhatSP()},
            {Log.e("NHAPSP","sent Fail! $myurl")})
        queue.add(stringRequest)
    }
    override fun onDestroy() {
        super.onDestroy()
        if (!NhapList.isEmpty()) {
            Log.e("test", "TONKHO onDestroy()")
            val gson = Gson()
            val data = gson.toJson(NhapList)
            val myurl = BIEN().local + "nhaphang.php?data1=$data"
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(
                Request.Method.GET, myurl,
                {
                    Log.e("NHAPSP", "sent OK! $myurl")
                },
                { Log.e("NHAPSP", "sent Fail! $myurl") })
            queue.add(stringRequest)
        }
    }
}