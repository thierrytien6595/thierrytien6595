package com.example.myapplication.QUANLYNHAPHANG

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.QUANLYHUYMON.APIService
import com.example.myapplication.QUANLYHUYMON.ServiceGenerator
import com.example.myapplication.QUANLYHUYMON.chitietdataItem
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_huymon.*
import kotlinx.android.synthetic.main.activity_nhaphang.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NHAPHANG : AppCompatActivity() {
    var chitienhaphangList = mutableListOf<chitietdataItemNhap>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nhaphang)
        chitietnhaphang()
    }

    private fun chitietnhaphang() {
        val serviceGenerator = ServiceGenerator.buildService(APIService::class.java)
        val call = serviceGenerator.chitietnhaphang()

        call.enqueue(object : Callback<MutableList<chitietdataItemNhap>> {
            override fun onResponse(
                call: Call<MutableList<chitietdataItemNhap>>,
                response: Response<MutableList<chitietdataItemNhap>>
            ) {
                if (response.isSuccessful) {
                    Log.e("SANPHAM",response.body()!!.toString())
                    chitienhaphangList.clear()
                    chitienhaphangList.addAll(response.body()!!)
                    hienthi()
                }
            }
            override fun onFailure(call: Call<MutableList<chitietdataItemNhap>>, t: Throwable) {
                t.printStackTrace()
                Log.e("MAIN2", t.message.toString())
            }
        })
    }

    private fun hienthi() {
        expandableNhapHang.setAdapter(com.example.myapplication.QUANLYNHAPHANG.ExpandableNhapListAdapter(this,chitienhaphangList))
    }
}