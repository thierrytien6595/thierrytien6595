package com.example.myapplication.QUANLYHUYMON

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.R
import kotlinx.android.synthetic.main.activity_huymon.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class huymon : AppCompatActivity() {
    var chitiethuymonList = mutableListOf<chitietdataItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_huymon)
        laychitiethuymon()
    }

    private fun laychitiethuymon() {
        val serviceGenerator = ServiceGenerator.buildService(APIService::class.java)
        val call = serviceGenerator.chitiethuymon()

        call.enqueue(object : Callback<MutableList<chitietdataItem>> {
            override fun onResponse(
                call: Call<MutableList<chitietdataItem>>,
                response: Response<MutableList<chitietdataItem>>
            ) {
                if (response.isSuccessful) {
                    Log.e("SANPHAM",response.body()!!.toString())
                    chitiethuymonList.clear()
                    chitiethuymonList.addAll(response.body()!!)
                    hienthi()
                }
            }

            override fun onFailure(call: Call<MutableList<chitietdataItem>>, t: Throwable) {
                t.printStackTrace()
                Log.e("MAIN2", t.message.toString())
            }
        })
    }

    private fun hienthi() {
        expandableListView.setAdapter(com.example.myapplication.QUANLYHUYMON.ExpandableListAdapter(this,chitiethuymonList))

    }

}
