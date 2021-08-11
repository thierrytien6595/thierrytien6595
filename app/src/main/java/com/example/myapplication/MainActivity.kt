package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.example.myapplication.QUANLYHUYMON.APIService
import com.example.myapplication.QUANLYHUYMON.ServiceGenerator
import com.example.myapplication.QUANLYHUYMON.huymon
import com.example.myapplication.QUANLYNHANVIEN.NHANVIEN
import com.example.myapplication.QUANLYNHAPHANG.NHAPHANG
import com.example.myapplication.THUNHAP.quanlyItem
import com.example.myapplication.TONKHO.TONKHO
import com.github.mikephil.charting.data.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    var ID:Int=1
    var qllist = mutableListOf<quanlyItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.e("ID",ID.toString())
        getdata(ID)
        var options = arrayOf("Theo giờ","Theo ngày","Theo Tuần","Theo Tháng")
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,options)
        autoComplete.threshold=0
        autoComplete.setAdapter(adapter)
        autoComplete.setOnItemClickListener { parent, view, position, id ->
            ID=position
            Log.e("ID",ID.toString())
            getdata(ID)
        }
    }

    override fun onResume() {
        super.onResume()
        getdata(ID)
        Log.e("ID",ID.toString())
    }
    private fun getdata(ID:Int) {
        val serviceGenerator = ServiceGenerator.buildService(APIService::class.java)
        val call = serviceGenerator.quanlydata(ID)

        call.enqueue(object : Callback<MutableList<quanlyItem>> {
            override fun onResponse(
                call: Call<MutableList<quanlyItem>>,
                response: Response<MutableList<quanlyItem>>
            ) {
                if (response.isSuccessful) {
                    Log.e("SANPHAM",response.body()!!.toString())

                    hienthi(response.body()!!)
                }
            }

            override fun onFailure(call: Call<MutableList<quanlyItem>>, t: Throwable) {
                t.printStackTrace()
                Log.e("MAIN2", t.message.toString())
            }
        })
    }

    private fun hienthi(body: MutableList<quanlyItem>) {
        qllist.clear()
        qllist.addAll(body)
        tv_tongtien.text = (qllist[0].TONGTIEN.toInt()/1000).toString()+"K"
        tv_sobanpv.text = qllist[0].SOBANPV+" bàn đang phục vụ"
        tv_somonpv.text = qllist[0].SOMONPV + " món đã phục vụ"
        tv_somoncpv.text = qllist[0].SOMONCPV + " món chờ phục vụ"
        tv_sobancpv.text = qllist[0].SOBANCPV + " BÀN CHƯA SỦ DỤNG"
        tv_doanhthu.text = (qllist[0].DTUOCTINH.toInt()/1000).toString() + "K"
        vebieudo()
    }

    private fun vebieudo() {
        var barlist = arrayListOf<BarEntry>()
        for(i in 0 until qllist[0].DOANHTHU.size){
            barlist.add(BarEntry(qllist[0].DOANHTHU[i].ID.toFloat(),qllist[0].DOANHTHU[i].DOANHTHU.toFloat()/1000))
        }
        var barDataSet = BarDataSet(barlist,"DOANH THU")
        var barData = BarData(barDataSet)
        barchart.data=barData
        barchart.setBackgroundColor(Color.WHITE)
        barDataSet.setColors(Color.GREEN)
        barDataSet.valueTextSize=8f
        barchart.notifyDataSetChanged()
        barchart.invalidate()
//        barData.barWidth=4f
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
            R.id.nhanvien -> {
                val intent = Intent(this, NHANVIEN::class.java)
                startActivity(intent)
                return true
            }
            R.id.chitiethuymon -> {
                val intent = Intent(this, huymon::class.java)
                startActivity(intent)
                return true
            }
            R.id.tonkho -> {
                val intent = Intent(this, TONKHO::class.java)
                startActivity(intent)
                return true
            }
            R.id.chitietnhaphang -> {
                val intent = Intent(this, NHAPHANG::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}