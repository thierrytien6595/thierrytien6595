package com.example.thachcoffee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.thachcoffee.BAN.ApiServiceBan
import com.example.thachcoffee.BAN.BanAdapter
import com.example.thachcoffee.BAN.BanModel
import com.example.thachcoffee.BAN.ServiceGenerator
import com.example.thachcoffee.SANPHAM.SPService
import com.example.thachcoffee.SPDACHON.SPDaChonAdapter
import com.example.thachcoffee.SPDACHON.SPDaChonModel
import com.example.thachcoffee.THEMXOASP.TachDonAdapter
import com.example.thachcoffee.THEMXOASP.ThemXoaSPAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_san_pham.*
import kotlinx.android.synthetic.main.activity_san_pham_da_chon.*
import kotlinx.android.synthetic.main.spdc_item.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class SanPhamDaChon : AppCompatActivity(),SPDaChonAdapter.OnItemClickListener,
    View.OnClickListener, ThemXoaSPAdapter.OnItemClickListener, BanAdapter.OnItemClickListener,
    TachDonAdapter.OnItemClickListener {
    val SPDaChonList = mutableListOf<SPDaChonModel>()
    val XoaMonList = mutableListOf<SPDaChonModel>()
    var banList = mutableListOf<BanModel>()
    var feature:String=""
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
                    Log.e("SANPHAM",response.body().toString())
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


    // T??CH ????N ADAPTER
    override fun onItemDaChonClick(data: SPDaChonModel) {

    }

    override fun onBtnGiamClick(data: SPDaChonModel) {

    }
    // END T??CH ????N ADAPTER
    override fun onBtnGiamClick(data: SPDaChonModel,type:Int) {
        Log.e("SANPHAMSPDC","TENSP: "+data.TENSP+"   SOLUONG: "+data.SOLUONG)
        if(type==0) {
            Log.e("SANPHAM:","type =0")
            val index = SPDaChonList.lastIndexOf(SPDaChonList.findLast { it.TENSP == data.TENSP })
            if (index != -1) {
                SPDaChonList.set(index, SPDaChonModel(data.TENSP, SPDaChonList[index].SOLUONG - 1,"",0,data.DONGIA,data.MonPhu))
                if (SPDaChonList[index].SOLUONG == 0) SPDaChonList.removeAt(index)
                rev_dachon.adapter?.notifyDataSetChanged()
            }

            val index1 = XoaMonList.lastIndexOf(XoaMonList.findLast { it.TENSP == data.TENSP })
            if (index1 == -1) // N???u SP ch???n ch??a c?? trong list
            {
                XoaMonList.add(SPDaChonModel(data.TENSP, 1,data.CHUTHICH,data.TRANGTHAIMON,data.DONGIA,data.MonPhu))
            } else {// N???u c?? r???i th?? t??ng s??? l?????ng l??n
                XoaMonList.set(index1, SPDaChonModel(data.TENSP, XoaMonList[index1].SOLUONG + 1,"",0,data.DONGIA,data.MonPhu))
            }
            if (XoaMonList != emptyList<SPDaChonModel>()) {
                layoutxoamon.visibility = View.VISIBLE
                rev_xoamon.adapter?.notifyDataSetChanged()
            } else {
                layoutxoamon.visibility = View.GONE
            }
        }else{
            Log.e("SANPHAM:","type =1")
            val index = XoaMonList.lastIndexOf(XoaMonList.findLast { it.TENSP == data.TENSP })
            if (index != -1) {
                XoaMonList.set(index, SPDaChonModel(data.TENSP, XoaMonList[index].SOLUONG - 1,"",0,data.DONGIA,data.MonPhu))
                if (XoaMonList[index].SOLUONG == 0) XoaMonList.removeAt(index)
                rev_xoamon.adapter?.notifyDataSetChanged()
            }

            val index1 = SPDaChonList.lastIndexOf(SPDaChonList.findLast { it.TENSP == data.TENSP })
            if (index1 == -1) // N???u SP ch???n ch??a c?? trong list
            {
                SPDaChonList.add(SPDaChonModel(data.TENSP, 1,data.CHUTHICH,data.TRANGTHAIMON,data.DONGIA,data.MonPhu))
            } else {// N???u c?? r???i th?? t??ng s??? l?????ng l??n
                SPDaChonList.set(index1, SPDaChonModel(data.TENSP, SPDaChonList[index1].SOLUONG + 1,"",0,data.DONGIA,data.MonPhu))
            }
            if (SPDaChonList != emptyList<SPDaChonModel>()) {
                rev_dachon.adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            btn_them_spdc.id->themmon()
            btn_thanhtoan.id->thanhtoan()
            btn_thongbao_xoamon.id->thongbao()
        }
    }

    private fun thanhtoan() {
        val myurl = bien().localhost+"thanhtoan.php?tenban=$tenban"
        Log.e("SANPHAM",myurl)
        Print(SPDaChonList)
        sentGet(myurl)
    }

    private fun Print(List: MutableList<SPDaChonModel>) {
        val formatter = SimpleDateFormat("HH:mm dd/MM/yyyy")
        var date: Date = Calendar.getInstance().time
        val now = formatter.format(date)
        val TITLE = "<CENTER><BIG>THACH COFFEE<BR><BR>"
        val TenBan = tenban?.let { bien().convert(it) }
        val datetime = "<CENTER>$now     <MEDIUM1><BOLD>$TenBan<BR><CENTER><LINE>"
        var list = "<LEFT>MON      ;;SL  DON GIA   TIEN<BR><CENTER><LINE>"
        var tongtien = 0
        for (i in 0 until List.size){
            val tensp = List[i].TENSP
            val soluong = List[i].SOLUONG.toString()
            val dongia = (List[i].DONGIA.toInt()/1000).toString()
            val tinhtien = (soluong.toInt()*dongia.toInt()).toString()
            tongtien += tinhtien.toInt()
            list+= "<LEFT>$tensp;;$soluong     $dongia"+"K      $tinhtien"+"K<BR>"
        }


        list+= "<CENTER><DLINE><BR><CENTER><MEDIUM1>TONG TIEN       $tongtien"+"K<BR><CENTER><DLINE><CUT>"
        var text = TITLE+datetime+list
        text = bien().convert(text)
        Log.e("test",text)
        val intent = Intent("pe.diegoveloper.printing")
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(intent)
    }


    private fun hienxoamon() {
        feature = "X??a M??n"
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

        if (feature=="T??ch ????n"){
            feature="Thanh To??n"
            btn_thongbao_xoamon.text= "THANH TO??N"
            laydanhsachban()
            return
        }
        if (feature=="Thanh To??n"){
            val gson = Gson()
            val data = gson.toJson(XoaMonList)
            Log.e("SANPHAM","??ang trong feature Thanh to??n")
            btn_thongbao_xoamon.text= "TH??NG B??O"
            val myurl = bien().localhost+"thanhtoan.php?tenban=$tenban&jsondata=$data"
            sentGet(myurl)
            return
        }
        if(feature=="X??a M??n")
        {
            if(edt_lydo.text.isEmpty() && btn_thongbao_xoamon.text!="THANH TO??N"){
                edt_lydo.hint = "CH??A NH???P L?? DO K??A M???Y ?????A"
                Toast.makeText(this, "Nh???p l?? do h???y m??n c??? th??? nha m???y em!", Toast.LENGTH_SHORT).show()
            }else {
                val gson = Gson()
                val data = gson.toJson(XoaMonList)
                val lydo = edt_lydo.text.trim()
                val manv = 1
                var myurl = bien().localhost+"lydohuymon.php?MANV=$manv&LYDO=$lydo&TENBAN=$tenban&DATA=$data"
                sentURL(myurl)
                myurl = bien().localhost+"xoamon.php?TENBAN=$tenban&jsondata=$data"
                sentGet(myurl)
                return
            }
        }
    }
    private fun chuyenvemain() {
        val intent: Intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.chuyenban -> {
                feature="Chuy???n B??n"
                chuyenban()
                true
                }
            R.id.tachdon-> {
                edt_lydo.visibility=View.GONE
                btn_thongbao_xoamon.text = "T??CH ????N"
                tachdon()
                btn_thanhtoan.visibility = View.INVISIBLE
                true
            }
            R.id.xoamon-> {
                edt_lydo.visibility=View.VISIBLE
                btn_thongbao_xoamon.text="H???Y M??N"
                hienxoamon()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    private fun tachdon(){
        feature = "T??ch ????n"
        rev_dachon.apply {
            adapter = ThemXoaSPAdapter(SPDaChonList,this@SanPhamDaChon,0,1)
        }
        rev_xoamon.apply {
            layoutManager = LinearLayoutManager(this@SanPhamDaChon)
            adapter = TachDonAdapter(XoaMonList,this@SanPhamDaChon)
        }
    }

    private fun chuyenban(){
        laydanhsachban()
    }

    private fun laydanhsachban() {
        val serviceGenerator = ServiceGenerator.buildService(ApiServiceBan::class.java)
        val call = serviceGenerator.getSP()
        Log.e("SANPHAM","L???y danh s??ch b??n trong laydanhsachban()")
        call.enqueue(object : Callback<MutableList<BanModel>> {
            override fun onResponse(
                call: Call<MutableList<BanModel>>,
                response: Response<MutableList<BanModel>>
            ) {
                if (response.isSuccessful) {
                    rev_dachon.apply {
                        layoutManager = GridLayoutManager(this@SanPhamDaChon,3)
                        adapter = BanAdapter(response.body()!!, this@SanPhamDaChon)
                    }
                }
            }

            override fun onFailure(call: Call<MutableList<BanModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("MAIN2", t.message.toString())
            }
        })
    }


    override fun onItemClick(data: BanModel) {
        if (feature=="Chuy???n B??n") {
            Log.e("SANPHAM6","???? chuy???n t??? b??n $tenban ?????n b??n ${data.TENBAN}")
            if (data.TRANGTHAI == 0) {
                val banchuyentoi = data.TENBAN
                val url = bien().localhost+"chuyenban.php?TENBAN=$tenban&banchuyentoi=$banchuyentoi"
                sentGet(url)
                return
            }else{
                val MABANgheptoi = data.MABAN
                val url = bien().localhost+"ghepban.php?TENBAN=$tenban&MABANgheptoi=$MABANgheptoi"
                sentGet(url)
                return
            }
        }
        if(feature=="Thanh To??n"){
            val gson = Gson()
            val data1 = gson.toJson(XoaMonList)
            Log.e("SANPHAM6","Data: "+data1.toString()+"<br> v?? T??n B??n: "+data.TENBAN)
            val tenbanchuyentoi = data.TENBAN
            val url = bien().localhost+"add_bill.php?TENBAN=$tenbanchuyentoi&jsondata=$data1"
            val queue = Volley.newRequestQueue(this)
            val stringRequest = StringRequest(Request.Method.GET, url,
                {   Log.e("SANPHAMSPDC","$url")
                    btn_thanhtoan.visibility=View.VISIBLE
                    thongbao()
                },
                {   Log.e("SANPHAMSPDC","$url")})
            queue.add(stringRequest)
            feature="X??a M??n"
            return
        }
    }

    private fun sentGet(url: String) {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url,
            {
                Log.e("SANPHAMSPDC","$url")
                chuyenvemain()},
            {Log.e("SANPHAMSPDC","$url")})
        queue.add(stringRequest)
        }
    private fun sentURL(url: String) {
        val queue = Volley.newRequestQueue(this)
        val stringRequest = StringRequest(Request.Method.GET, url,
            {
                Log.e("SANPHAMSPDC","$url")
                },
            {Log.e("SANPHAMSPDC","$url")})
        queue.add(stringRequest)
    }
}


