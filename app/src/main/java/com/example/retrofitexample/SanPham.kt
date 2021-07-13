package com.example.retrofitexample
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitexample.BAN.ServiceGenerator
import com.example.retrofitexample.SANPHAM.SPService
import com.example.retrofitexample.SANPHAM.SanPhamAdapter
import com.example.retrofitexample.SANPHAM.SanPhamModel
import com.example.retrofitexample.SPDACHON.SPDaChonAdapter
import com.example.retrofitexample.SPDACHON.SPDaChonModel
import com.example.retrofitexample.SPDACHON.listmon
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_san_pham.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SanPham : AppCompatActivity(), SanPhamAdapter.OnItemClickListener,SPDaChonAdapter.OnItemClickListener,View.OnClickListener {
    val SPDaChonList = mutableListOf<SPDaChonModel>()
    var tenban: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_san_pham)
        val intent = intent
        tenban = intent.getStringExtra("TENBAN")
        tv_ban_chonmon.text = tenban
        btn_thongbao.visibility = View.GONE
        setonclickbtn()
        rev_chonmon2.apply {
            layoutManager = LinearLayoutManager(this@SanPham)
            adapter = SPDaChonAdapter(SPDaChonList,this@SanPham)
        }
        laydanhsachmon()
//        laydanhsachmondachon(tenban!!)
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
                    rev_chonmon2.apply {
                        SPDaChonList.clear()
                        SPDaChonList.addAll(response.body()!!)
                        Log.e("SANPHAM1",response.body()!!.toString())
                        adapter?.notifyDataSetChanged()
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<SPDaChonModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("SANPHAM2", t.message.toString())
            }
        })
    }

    private fun laydanhsachmon() {
        val serviceGenerator = ServiceGenerator.buildService(SPService::class.java)
        val call = serviceGenerator.getSP()
        call.enqueue(object : Callback<MutableList<SanPhamModel>> {
            override fun onResponse(
                call: Call<MutableList<SanPhamModel>>,
                response: Response<MutableList<SanPhamModel>>
            ) {
                if (response.isSuccessful) {
                    rev_chonmon.apply {
                        layoutManager = LinearLayoutManager(this@SanPham)
                        adapter = SanPhamAdapter(response.body()!!,this@SanPham)
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<SanPhamModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("SANPHAM3", t.message.toString())
            }
        })
    }
    private fun setonclickbtn() {
        btn_caphe.setOnClickListener(this)
        btn_nuocngot.setOnClickListener(this)
        btn_tra.setOnClickListener(this)
        btn_sinhto.setOnClickListener(this)
        btn_nuocep.setOnClickListener(this)
        btn_yogurt.setOnClickListener(this)
        btn_soda.setOnClickListener(this)
        btn_daxay.setOnClickListener(this)
        btn_kem.setOnClickListener(this)
        btn_thuoc.setOnClickListener(this)
        btn_doan.setOnClickListener(this)
        btn_khac.setOnClickListener(this)
        btn_thongbao.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
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
            btn_thongbao.id-> thongbao()
        }
    }

    private fun thongbao() {
        val gson = Gson()
        val data = gson.toJson(SPDaChonList)
        Log.e("SANPHAM6",data.toString()+ tenban.toString())
        val serviceGenerator = ServiceGenerator.buildService(SPService::class.java)
        val call = serviceGenerator.insertbill(tenban.toString(), data)
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

    private fun nhaydenvitri(index :Int) {
        (rev_chonmon.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(index,0)
    }
    override fun onItemClick(data: SanPhamModel) {
        val index = SPDaChonList.lastIndexOf(SPDaChonList.findLast {it.TENSP==data.TENSP})
        if (index==-1) // Nếu SP chọn chưa có trong list
        {
            SPDaChonList.add(SPDaChonModel(data.TENSP, 1))
        }
        else{// Nếu có rồi thì tăng số lượng lên
            SPDaChonList.set(index, SPDaChonModel(data.TENSP,SPDaChonList[index].SOLUONG+1))
        }
        if (SPDaChonList != emptyList<SPDaChonModel>()) {
            btn_thongbao.visibility=View.VISIBLE
            rev_chonmon2.adapter?.notifyDataSetChanged()
        }
        else {
            btn_thongbao.visibility = View.INVISIBLE
        }
    }

    override fun onBtnGiamClick(tensp: String) {
        val index = SPDaChonList.lastIndexOf(SPDaChonList.findLast {it.TENSP==tensp})
        if(index!=-1) {
            SPDaChonList.set(index, SPDaChonModel(tensp, SPDaChonList[index].SOLUONG - 1))
            if (SPDaChonList[index].SOLUONG == 0) SPDaChonList.removeAt(index)
            rev_chonmon2.adapter?.notifyDataSetChanged()
        }
    }

    override fun onItemDaChonClick(data: SPDaChonModel) {
        val index = SPDaChonList.lastIndexOf(SPDaChonList.findLast {it.TENSP==data.TENSP})
        SPDaChonList.set(index, SPDaChonModel(data.TENSP,SPDaChonList[index].SOLUONG,"KHÔNG ĐƯỜNG"))
        rev_chonmon2.adapter?.notifyDataSetChanged()
    }
}