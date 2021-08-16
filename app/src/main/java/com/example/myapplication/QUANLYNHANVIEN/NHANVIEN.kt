package com.example.myapplication.QUANLYNHANVIEN
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.example.myapplication.variable.BIEN
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.android.synthetic.main.fragment_lich_n_v.*
import kotlinx.android.synthetic.main.fragment_them_n_v.*

class NHANVIEN : AppCompatActivity(){
    val localhost = BIEN().url()

    private lateinit var themNVFragment: ThemNVFragment
    private lateinit var danhsachNVFragment: DanhsachNVFragment
    private lateinit var lichNVFragment: LichNVFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nhanvien)
        themNVFragment = ThemNVFragment()
        danhsachNVFragment = DanhsachNVFragment()
        lichNVFragment = LichNVFragment()
        setFragment(lichNVFragment)
    }

    private fun setFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragment,fragment)
        fragmentTransaction.commit()
    }

    fun onbtnClick(v: View?) {
        when (v?.id) {
            btn_luu.id -> {
                val TENNV:String=tennhanvien.text.toString().trim()
                val LUONG=luongnhanvien.text.toString().trim()
                val SDT=sdt.text.toString().trim()
                val MANV = manv.text

                val myurl = localhost+"nhanvien.php?MANV=$MANV&TENNV=$TENNV&LUONG=$LUONG&SDT=$SDT"
                val queue = Volley.newRequestQueue(this)
                val stringRequest = StringRequest(
                    Request.Method.GET, myurl,
                    {
                        setFragment(danhsachNVFragment)
                        Log.e("NHAPSP","sent OK! $myurl") },
                    {Log.e("NHAPSP","sent Fail! $myurl")})
                queue.add(stringRequest)
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_nhanvien, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.themnhanvien -> {
                setFragment(themNVFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}