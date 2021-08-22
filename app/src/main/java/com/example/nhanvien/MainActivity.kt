package com.example.nhanvien

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nhanvien.API.APIService
import com.example.nhanvien.API.ServiceGenerator
import com.example.nhanvien.API.timeItem
import com.example.nhanvien.DIALOG.WorkTimeDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.worktimedialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var nvlist = arrayListOf<String>()
    val dialogfragment = WorkTimeDialog()
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
        initautocomlete()
        autoCompletenhanvien.setOnItemClickListener { parent, view, position, id ->
            capnhatchamcong(nvlist[position])
        }
    }

    private fun initautocomlete() {
        danhsach()
        val autocompletenhanvien= findViewById<AutoCompleteTextView>(R.id.autoCompletenhanvien)
        var adapter =ArrayAdapter(this,android.R.layout.simple_list_item_1,nvlist)
        autocompletenhanvien?.threshold=0
        autocompletenhanvien?.setAdapter(adapter)
    }
    private fun danhsach() {
        val serviceGenerator = ServiceGenerator.buildService(APIService::class.java)
        val call = serviceGenerator.danhsachnhanvien()
        call.enqueue(object : Callback<MutableList<danhsachmodel>> {
            override fun onResponse(
                call: Call<MutableList<danhsachmodel>>,
                response: Response<MutableList<danhsachmodel>>
            ) {
                if (response.isSuccessful) {
                    updateDS(response.body())
                }
            }
            override fun onFailure(call: Call<MutableList<danhsachmodel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("SANPHAM3", t.message.toString())
            }
        })
    }

    private fun updateDS(body: MutableList<danhsachmodel>?) {
        nvlist.clear()
        for (i in 0 until body!!.size){
            nvlist.add(body[i].TENNV)
        }
    }
    fun onClick(v : View?){
        when(v?.id){
            btn_vao.id-> ckick_btn_vao()
            btn_ra.id -> click_btn_ra()
            dialogfragment.btn_xacnhan.id->{click_xacnhan()}
        }
    }
    private fun click_xacnhan() {
        sentData("ra",autoCompletenhanvien.text.toString(),dialogfragment.edt_giolam.text.toString())
        dialogfragment.dismiss()
        capnhatchamcong(autoCompletenhanvien.text.toString())
    }

    private fun click_btn_ra() {
        dialogfragment.show(supportFragmentManager,"worktimedialogfragment")
        btn_vao.visibility=View.GONE
        btn_ra.visibility=View.GONE
    }
    private fun ckick_btn_vao() {
        sentData("vao",autoCompletenhanvien.text.toString())
        btn_vao.visibility=View.GONE
        btn_ra.visibility=View.GONE
    }
    private fun sentData(feature:String, tennv:String, giolam: String="0"){
        val myurl = BIEN().localhost+"chamcong.php?feature=$feature&tennv=$tennv&giolam=$giolam"
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(
            Request.Method.GET, myurl,
            {
                Log.e("NHAPSP","sent OK! $myurl")
                capnhatchamcong(autoCompletenhanvien.text.toString())},
            { Log.e("NHAPSP","sent Fail! $myurl")})
        queue.add(stringRequest)
    }
    private fun capnhatchamcong(tennv: String) {
        val serviceGenerator = ServiceGenerator.buildService(APIService::class.java)
        val call = serviceGenerator.chamcong(tennv)
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
        var tong = 0.0
        for (i in 0 until chamcongList.size){
            val number= chamcongList[i].WORKTIME
            tong += number.toDouble()
        }
        tong = Math.round(tong*10.0)/10.0
        tv_tonggiolam.text = tong.toString()
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