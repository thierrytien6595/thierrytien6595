package com.example.retrofitexample
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.retrofitexample.BAN.ApiServiceBan
import com.example.retrofitexample.BAN.BanAdapter
import com.example.retrofitexample.BAN.BanModel
import com.example.retrofitexample.BAN.ServiceGenerator
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_main.view.*
class MainActivity : AppCompatActivity(),BanAdapter.OnItemClickListener,View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    val serviceGenerator = ServiceGenerator.buildService(ApiServiceBan::class.java)
    val call = serviceGenerator.getPosts()

    call.enqueue(object : Callback<MutableList<BanModel>> {
        override fun onResponse(
            call: Call<MutableList<BanModel>>,
            response: Response<MutableList<BanModel>>
        ) {
            if (response.isSuccessful) {
                recycler_view.apply {
                    layoutManager = GridLayoutManager(this@MainActivity,3)
                    adapter = BanAdapter(response.body()!!, this@MainActivity)
                }
            }
        }

        override fun onFailure(call: Call<MutableList<BanModel>>, t: Throwable) {
            t.printStackTrace()
            Log.e("ppppp", t.message.toString())
        }
    })
    }

    override fun onItemClick(data: BanModel) {
        val intent : Intent = Intent(this, SanPham::class.java)
        intent.putExtra("TENBAN",data.TENBAN)
        startActivity(intent)
    }

    override fun onClick(v: View?) {
    }
}