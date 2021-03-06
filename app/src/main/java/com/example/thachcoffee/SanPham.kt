package com.example.thachcoffee
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.thachcoffee.BAN.ServiceGenerator
import com.example.thachcoffee.SANPHAM.SPService
import com.example.thachcoffee.SANPHAM.SanPhamAdapter
import com.example.thachcoffee.SANPHAM.SanPhamModel
import com.example.thachcoffee.SPDACHON.SPDaChonAdapter
import com.example.thachcoffee.SPDACHON.SPDaChonModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_san_pham.*
import kotlinx.android.synthetic.main.dachon_item.*
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
        group_spdc.visibility = View.GONE
        setonclickbtn()
        rev_chonmon2.apply {
            layoutManager = LinearLayoutManager(this@SanPham)
            adapter = SPDaChonAdapter(SPDaChonList,this@SanPham)
        }
        laydanhsachmon()
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
                        rev_chonmon2.adapter?.notifyDataSetChanged()
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
                    Log.e("SANPHAM",response.body().toString())
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
        val myurl = bien().localhost+"add_bill.php?TENBAN=$tenban&jsondata=$data"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, myurl,
            {
                Log.e("SANPHAMSPDC","sent OK! $myurl")
                chuyenvemain()},
            {Log.e("SANPHAMSPDC","sent Fail! $myurl")})
        queue.add(stringRequest)

    }

    private fun chuyenvemain() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun nhaydenvitri(index :Int) {
        (rev_chonmon.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(index,0)
    }
    override fun onItemClick(data: SanPhamModel) {
        val index = SPDaChonList.lastIndexOf(SPDaChonList.findLast { it.TENSP == data.TENSP })
        if (index == -1) // N???u SP ch???n ch??a c?? trong list
        {
            if (data.SOLUONG.toInt() != 0) {
                SPDaChonList.add(SPDaChonModel(data.TENSP, 1, "", 0, data.GIASP,data.MonPhu))
            } else {
                Toast.makeText(this, "H???t r???i c???n nh???p th??m s???n ph???m", Toast.LENGTH_SHORT).show()
            }
        } else {// N???u c?? r???i th?? t??ng s??? l?????ng l??n
            if (data.MonPhu!=-1){
                if (SPDaChonList[index].SOLUONG == data.SOLUONG.toInt()) {
                    Toast.makeText(this, "QU?? S??? L?????NG TRONG KHO", Toast.LENGTH_SHORT).show()
                } else {
                    SPDaChonList.set(index, SPDaChonModel(data.TENSP, SPDaChonList[index].SOLUONG + 1, "", 0, data.GIASP,data.MonPhu))
                }
            }else{
                SPDaChonList.set(index, SPDaChonModel(data.TENSP, SPDaChonList[index].SOLUONG + 1, "", 0, data.GIASP,data.MonPhu))
            }
            }
        if (SPDaChonList != emptyList<SPDaChonModel>()) {
            group_spdc.visibility=View.VISIBLE
            rev_chonmon2.adapter?.notifyDataSetChanged()
        }
        else {
            group_spdc.visibility = View.INVISIBLE
            }
    }

    override fun onBtnGiamClick(tensp: String) {
        val index = SPDaChonList.lastIndexOf(SPDaChonList.findLast {it.TENSP==tensp})
        if(index!=-1) {
            SPDaChonList.set(index, SPDaChonModel(tensp, SPDaChonList[index].SOLUONG - 1,"",0,SPDaChonList[index].DONGIA))
            if (SPDaChonList[index].SOLUONG == 0) SPDaChonList.removeAt(index)
            rev_chonmon2.adapter?.notifyDataSetChanged()
        }
    }

    override fun onItemDaChonClick(data: SPDaChonModel) {
        val index = SPDaChonList.lastIndexOf(SPDaChonList.findLast {it.TENSP==data.TENSP})
        SPDaChonList.set(index, SPDaChonModel(data.TENSP,SPDaChonList[index].SOLUONG,
            edittext.text.toString(),SPDaChonList[index].TRANGTHAIMON,SPDaChonList[index].DONGIA
        ))
        rev_chonmon2.adapter?.notifyDataSetChanged()
    }
}