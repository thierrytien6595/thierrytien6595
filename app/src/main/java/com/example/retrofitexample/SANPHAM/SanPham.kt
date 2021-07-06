package com.example.retrofitexample.SANPHAM
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofitexample.BAN.ServiceGenerator
import com.example.retrofitexample.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_san_pham.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SanPham : AppCompatActivity(),SanPhamAdapter.OnItemClickListener,View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_san_pham)
        gettenban()
        setonclickbtn()
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


    private fun gettenban() {
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
        Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show()
    }
}