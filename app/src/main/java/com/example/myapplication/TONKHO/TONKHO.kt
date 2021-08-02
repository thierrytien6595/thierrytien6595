package com.example.myapplication.TONKHO

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.QUANLYHUYMON.APIService
import com.example.myapplication.QUANLYHUYMON.ServiceGenerator
import com.example.myapplication.R
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
        group_nhapkho.visibility = View.GONE
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
        val call = serviceGenerator.getSP()
        call.enqueue(object : Callback<MutableList<SanPhamModel>> {
            override fun onResponse(
                call: Call<MutableList<SanPhamModel>>,
                response: Response<MutableList<SanPhamModel>>
            ) {
                if (response.isSuccessful) {
                    rev_chonmon.apply {
                        updatedata(response.body())
                    }
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
            btn_caphe.id -> nhaydenvitri(0)
            btn_nuocngot.id -> nhaydenvitri(15)
            btn_tra.id -> nhaydenvitri(33)
            btn_sinhto.id -> nhaydenvitri(47)
            btn_nuocep.id -> nhaydenvitri(56)
            btn_yogurt.id-> nhaydenvitri(72)
            btn_soda.id-> nhaydenvitri(81)
            btn_daxay.id-> nhaydenvitri(87)
            btn_kem.id-> nhaydenvitri(93)
            btn_doan.id-> nhaydenvitri(96)
            btn_thuoc.id -> nhaydenvitri(100)
            btn_khac.id -> nhaydenvitri(108)
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

    private fun nhaydenvitri(index :Int) {
        (rev_chonmon.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(index,0)
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
            group_nhapkho.visibility=View.VISIBLE
            rev_chonmon2.adapter?.notifyDataSetChanged()
        }
        else {
            group_nhapkho.visibility=View.GONE
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

    override fun onimvEDIT(MASP: String) {

    }

    private fun sentData(data: MutableList<SanPhamModel>){
        val gson = Gson()
        val data = gson.toJson(data)
        val myurl = "http://192.168.1.5/thach/nhaphang.php?data=$data"
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
        Log.e("test","TONKHO onDestroy()")
        val gson = Gson()
        val data = gson.toJson(NhapList)
        val myurl = "http://192.168.1.5/thach/nhaphang.php?data1=$data"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, myurl,
            {
                Log.e("NHAPSP","sent OK! $myurl")
            },
            {Log.e("NHAPSP","sent Fail! $myurl")})
        queue.add(stringRequest)
    }
}