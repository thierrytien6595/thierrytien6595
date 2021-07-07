package com.example.retrofitexample
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_san_pham.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SanPham : AppCompatActivity(), SanPhamAdapter.OnItemClickListener,View.OnClickListener {
    val SPDaChonList = mutableListOf<SPDaChonModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_san_pham)
        laytenban()
        setonclickbtn()
        laydanhsachmon()
    }

    private fun laydanhsachmon() {
        val serviceGenerator = ServiceGenerator.buildService(SPService::class.java)
        val call = serviceGenerator.getPosts()
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
                Log.e("ppppp", t.message.toString())
            }
        })
    }
    private fun laytenban() {
        val intent = intent
        val tenban: String? = intent.getStringExtra("TENBAN")
        tv_ban_chonmon.text = tenban }
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
            else -> nhaydenvitri(108)
        }
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
                SPDaChonList.set(index,SPDaChonModel(data.TENSP,SPDaChonList[index].SOLUONG+1))
            }
            if (SPDaChonList != null) {
                    rev_chonmon2.apply {
                        layoutManager = LinearLayoutManager(this@SanPham)
                        adapter = SPDaChonAdapter(SPDaChonList)
                    }
                }

    }
}