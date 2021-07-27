package com.example.myapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.ExpandableListAdapter
import com.example.myapplication.QUANLYHUYMON.huymon
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var barlist: ArrayList<BarEntry>
    lateinit var barDataSet: BarDataSet
    lateinit var barData:BarData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        barlist = ArrayList()
        barlist.add(BarEntry(10f,10f))
        barlist.add(BarEntry(15f,20f))
        barlist.add(BarEntry(20f,30f))
        barlist.add(BarEntry(25f,40f))
        barlist.add(BarEntry(30f,50f))
        barlist.add(BarEntry(35f,60f))
        barlist.add(BarEntry(40f,70f))
        barlist.add(BarEntry(45f,80f))
        barlist.add(BarEntry(100f,10f))
        barlist.add(BarEntry(150f,20f))
        barlist.add(BarEntry(200f,30f))
        barlist.add(BarEntry(250f,40f))
        barlist.add(BarEntry(300f,50f))
        barlist.add(BarEntry(350f,60f))
        barlist.add(BarEntry(400f,70f))
        barlist.add(BarEntry(450f,80f))

        barDataSet = BarDataSet(barlist,"Tien")
        barData = BarData(barDataSet)
        barchart.data=barData
        barchart.setBackgroundColor(Color.WHITE)
        barDataSet.setColors(Color.GREEN)
        barData.barWidth=4f
        var options = arrayOf("Hôm nay","Hôm qua","Tuần này","Tháng này")
        var adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,options)
        autoComplete.threshold=0
        autoComplete.setAdapter(adapter)
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
            R.id.chitiethuymon -> {
                val intent = Intent(this, huymon::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}