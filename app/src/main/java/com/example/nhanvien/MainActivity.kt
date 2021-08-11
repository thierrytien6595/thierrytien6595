package com.example.nhanvien

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nhanvien.API.APIService
import com.example.nhanvien.API.ServiceGenerator
import com.example.nhanvien.API.timeItem
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val manv=1;
    private val chamcongList = mutableListOf<timeItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_vao.visibility = View.GONE
        btn_ra.visibility = View.GONE
        rev_lichsu.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = MainAdapter(chamcongList,this@MainActivity)
        }
        capnhatchamcong()
    }

    fun onClick(v : View?){
        when(v?.id){
            btn_vao.id-> ckick_btn_vao()
            btn_ra.id -> click_btn_ra()
        }
    }

    private fun click_btn_ra() {
        sentData("ra",2)
        btn_vao.visibility=View.GONE
        btn_ra.visibility=View.GONE
    }
    private fun ckick_btn_vao() {
        sentData("vao",2)
        btn_vao.visibility=View.GONE
        btn_ra.visibility=View.GONE
    }
    private fun sentData(feature:String,manv:Int){
        val myurl = BIEN().localhost+"chamcong.php?feature=$feature&manv=$manv"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, myurl,
            {
                Log.e("NHAPSP","sent OK! $myurl")
                capnhatchamcong()},
            { Log.e("NHAPSP","sent Fail! $myurl")})
        queue.add(stringRequest)
    }
    private fun capnhatchamcong() {
        val serviceGenerator = ServiceGenerator.buildService(APIService::class.java)
        val call = serviceGenerator.chamcong(2)
        call.enqueue(object : Callback<MutableList<timeItem>> {
            override fun onResponse(
                call: Call<MutableList<timeItem>>,
                response: Response<MutableList<timeItem>>
            ) {
                if (response.isSuccessful) {
                    Log.e("SANPHAM3", response.body().toString())
                    updatedata(response.body())
                }
            }

            override fun onFailure(call: Call<MutableList<timeItem>>, t: Throwable) {
                t.printStackTrace()
                Log.e("SANPHAM3", t.message.toString())
            }
        })
    }

    private fun updatedata(body: MutableList<timeItem>?) {
        chamcongList.clear()
        chamcongList.addAll(body!!)
        rev_lichsu.adapter?.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.chamcong -> {
                btn_vao.visibility=View.VISIBLE
                btn_ra.visibility=View.VISIBLE
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}