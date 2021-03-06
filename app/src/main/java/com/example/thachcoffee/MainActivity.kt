package com.example.thachcoffee
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.thachcoffee.BAN.ApiServiceBan
import com.example.thachcoffee.BAN.BanAdapter
import com.example.thachcoffee.BAN.BanModel
import com.example.thachcoffee.BAN.ServiceGenerator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(),BanAdapter.OnItemClickListener,View.OnClickListener {
    var banList = mutableListOf<BanModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
        recycler_view.apply {
            layoutManager = GridLayoutManager(this@MainActivity,3)
            adapter = BanAdapter(banList, this@MainActivity)
        }
        laydanhsachban()
    }

    private fun laydanhsachban() {
        val serviceGenerator = ServiceGenerator.buildService(ApiServiceBan::class.java)
        val call = serviceGenerator.getSP()

        call.enqueue(object : Callback<MutableList<BanModel>> {
            override fun onResponse(
                call: Call<MutableList<BanModel>>,
                response: Response<MutableList<BanModel>>
            ) {
                if (response.isSuccessful) {
                    banList.clear()
                    banList.addAll(response.body()!!)
                    Log.e("SANPHAM",response.body()!!.toString())
                    recycler_view.adapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<MutableList<BanModel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("MAIN2", t.message.toString())
            }
        })
    }

    override fun onItemClick(data: BanModel) {
        if (data.TRANGTHAI==1){
            val intent : Intent = Intent(this, SanPhamDaChon::class.java)
            intent.putExtra("TENBAN",data.TENBAN)
            intent.putExtra("TRANGTHAIBAN",data.TRANGTHAI)
            Log.e("SANPHAM.MAIN",data.TRANGTHAI.toString())
            startActivity(intent)
        }
        else{
            val intent : Intent = Intent(this, SanPham::class.java)
            intent.putExtra("TENBAN",data.TENBAN)
            intent.putExtra("TRANGTHAIBAN",data.TRANGTHAI)
            Log.e("SANPHAM.MAIN",data.TRANGTHAI.toString())
            startActivity(intent)
        }
    }

    override fun onClick(v: View?) {
        val textToPrint = "Your text here"
        val intent = Intent("pe.diegoveloper.printing")
        intent.setAction(android.content.Intent.ACTION_SEND);
        intent.setAction(android.content.Intent.ACTION_SEND);
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, textToPrint)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        recycler_view.adapter?.notifyDataSetChanged()
//        Log.e("SANPHAM","load lai ban")
    }
}