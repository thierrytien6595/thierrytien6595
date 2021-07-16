package com.example.retrofitexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitexample.BAN.ServiceGenerator
import com.example.retrofitexample.SANPHAM.SPService
import com.example.retrofitexample.SPDACHON.SPDaChonAdapter
import com.example.retrofitexample.SPDACHON.SPDaChonModel
import com.example.retrofitexample.SPDACHON.listmon
import com.example.retrofitexample.THEMXOASP.ThemXoaSPAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_san_pham.*
import kotlinx.android.synthetic.main.activity_san_pham_da_chon.*
import kotlinx.android.synthetic.main.spdc_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SanPhamDaChon : AppCompatActivity(),SPDaChonAdapter.OnItemClickListener,
    View.OnClickListener, ThemXoaSPAdapter.OnItemClickListener {
    val SPDaChonList = mutableListOf<SPDaChonModel>()
    val XoaMonList = mutableListOf<SPDaChonModel>()
    var tenban: String? = null
    var xoa:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_san_pham_da_chon)
        val intent = intent
        tenban = intent.getStringExtra("TENBAN")
        layoutxoamon.visibility=View.GONE
        tv_tenban_spdc.text = tenban
        rev_dachon.apply {
            layoutManager = LinearLayoutManager(this@SanPhamDaChon)
            adapter = ThemXoaSPAdapter(SPDaChonList,this@SanPhamDaChon,xoa)
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
                    rev_dachon.adapter?.notifyDataSetChanged()
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

    override fun onBtnGiamClick(data: SPDaChonModel,type:Int) {
        Log.e("SANPHAMSPDC","TENSP: "+data.TENSP+"   SOLUONG: "+data.SOLUONG)
        if(type==0) {
            val index = SPDaChonList.lastIndexOf(SPDaChonList.findLast { it.TENSP == data.TENSP })
            if (index != -1) {
                SPDaChonList.set(index, SPDaChonModel(data.TENSP, SPDaChonList[index].SOLUONG - 1))
                if (SPDaChonList[index].SOLUONG == 0) SPDaChonList.removeAt(index)
                rev_dachon.adapter?.notifyDataSetChanged()
            }

            val index1 = XoaMonList.lastIndexOf(XoaMonList.findLast { it.TENSP == data.TENSP })
            if (index1 == -1) // Nếu SP chọn chưa có trong list
            {
                XoaMonList.add(SPDaChonModel(data.TENSP, 1))
            } else {// Nếu có rồi thì tăng số lượng lên
                XoaMonList.set(index1, SPDaChonModel(data.TENSP, XoaMonList[index1].SOLUONG + 1))
            }
            if (XoaMonList != emptyList<SPDaChonModel>()) {
                layoutxoamon.visibility = View.VISIBLE
                rev_xoamon.adapter?.notifyDataSetChanged()
            } else {
                layoutxoamon.visibility = View.GONE
            }
        }else{
            val index = XoaMonList.lastIndexOf(XoaMonList.findLast { it.TENSP == data.TENSP })
            if (index != -1) {
                XoaMonList.set(index, SPDaChonModel(data.TENSP, XoaMonList[index].SOLUONG - 1))
                if (XoaMonList[index].SOLUONG == 0) XoaMonList.removeAt(index)
                rev_xoamon.adapter?.notifyDataSetChanged()
            }

            val index1 = SPDaChonList.lastIndexOf(SPDaChonList.findLast { it.TENSP == data.TENSP })
            if (index1 == -1) // Nếu SP chọn chưa có trong list
            {
                SPDaChonList.add(SPDaChonModel(data.TENSP, 1))
            } else {// Nếu có rồi thì tăng số lượng lên
                SPDaChonList.set(index1, SPDaChonModel(data.TENSP, SPDaChonList[index1].SOLUONG + 1))
            }
            if (SPDaChonList != emptyList<SPDaChonModel>()) {
                rev_dachon.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            btn_them_spdc.id->themmon()
            btn_xoa_spdc.id->xoamon()
            btn_thongbao_xoamon.id->thongbao()
        }
    }

    private fun xoamon() {
        xoa+=1
        xoa=xoa%2
        rev_dachon.apply {
            adapter = ThemXoaSPAdapter(SPDaChonList,this@SanPhamDaChon,0,xoa)
        }
        rev_xoamon.apply {
            layoutManager = LinearLayoutManager(this@SanPhamDaChon)
            adapter = ThemXoaSPAdapter(XoaMonList,this@SanPhamDaChon,1,1)
        }

    }

    private fun themmon() {
        val intent : Intent = Intent(this, SanPham::class.java)
        intent.putExtra("TENBAN",tenban)
        startActivity(intent)
    }

    private fun thongbao() {
        val gson = Gson()
        val data = gson.toJson(XoaMonList)
        Log.e("SANPHAM6",data.toString()+ tenban.toString())
        val serviceGenerator = ServiceGenerator.buildService(SPService::class.java)
        val call = serviceGenerator.xoamon(tenban.toString(), data)
        //-------------------------//
        call.enqueue(object : Callback<MutableList<listmon>> {
            override fun onResponse(call: Call<MutableList<listmon>>, response: Response<MutableList<listmon>>){
                chuyenvemain()
            }
            override fun onFailure(call: Call<MutableList<listmon>>, t: Throwable) {
                Log.e("SANPHAM6",t.message.toString())
            }
        })
    }
    private fun chuyenvemain() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}