package com.example.myapplication.QUANLYNHANVIEN
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import kotlinx.android.synthetic.main.fragment_them_n_v.*

class NHANVIEN : AppCompatActivity() {

    private lateinit var themNVFragment: ThemNVFragment


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nhanvien)

        themNVFragment = ThemNVFragment()
//        setFragment(themNVFragment)
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
                val myurl = "http://192.168.1.5/thach/nhanvien.php?TENNV=$TENNV&LUONG=$LUONG&SDT=$SDT"
                val queue = Volley.newRequestQueue(this)
                val stringRequest = StringRequest(
                    Request.Method.GET, myurl,
                    {
                        Log.e("NHAPSP","sent OK! $myurl") },
                    {Log.e("NHAPSP","sent Fail! $myurl")})
                queue.add(stringRequest)
            }
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_nhanvien, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
//            R.id.xoanhanvien -> {
//                val intent = Intent(this, XoaNV::class.java)
//                startActivity(intent)
//                return true
//            }
            R.id.themnhanvien -> {
                setFragment(themNVFragment)
                return true
            }
//            R.id.suanhanvien -> {
//                val intent = Intent(this, SuaNV::class.java)
//                startActivity(intent)
//                return true
//            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}