package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitexample.BAN.ServiceGenerator
import com.example.retrofitexample.SANPHAM.SPService
import com.example.retrofitexample.SPDACHON.SPDaChonAdapter
import com.example.retrofitexample.SPDACHON.SPDaChonModel
import kotlinx.android.synthetic.main.activity_san_pham.*
import kotlinx.android.synthetic.main.activity_san_pham_da_chon.*
import kotlinx.android.synthetic.main.activity_san_pham_da_chon.rev_chonmon
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SanPhamDaChon : AppCompatActivity(),SPDaChonAdapter.OnItemClickListener{
    val SPDaChonList = mutableListOf<SPDaChonModel>()
    var tenban: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_san_pham_da_chon)
        val intent = intent
        tenban = intent.getStringExtra("TENBAN")
        tv_tenban_spdc.text = tenban
        rev_chonmon.apply {
            layoutManager = LinearLayoutManager(this@SanPhamDaChon)
            adapter = SPDaChonAdapter(SPDaChonList,this@SanPhamDaChon)
        }
        laydanhsachmondachon(tenban!!)

    }

    private fun laydanhsachmondachon(tenban:String) {
        val serviceGenerator = ServiceGenerator.buildService(SPService::class.java)
        val call = serviceGenerator.getspdachon(tenban)
        call.enqueue(object : Callback<MutableList<SPDaChonModel>> {
            override fun onResponse(
                call: Call<MutableList<SPDaChonModel>>,
                response: Response<MutableList<SPDaChonModel>>
            ) {
                if (response.isSuccessful) {
                    SPDaChonList.clear()
                    SPDaChonList.addAll(response.body()!!)
                    rev_chonmon.adapter?.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<MutableList<SPDaChonModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("SANPHAM2", t.message.toString())
            }
        })
    }

    override fun onItemDaChonClick(data: SPDaChonModel) {

    }
}