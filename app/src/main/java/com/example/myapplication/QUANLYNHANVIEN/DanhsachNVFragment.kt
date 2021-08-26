package com.example.myapplication.QUANLYNHANVIEN

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.QUANLYHUYMON.APIService
import com.example.myapplication.QUANLYHUYMON.ServiceGenerator
import com.example.myapplication.R
import com.example.myapplication.TONKHO.SanPhamModel
import com.example.myapplication.variable.BIEN
import kotlinx.android.synthetic.main.activity_tonkho.*
import kotlinx.android.synthetic.main.fragment_danhsach_n_v.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.ArrayList

class DanhsachNVFragment : Fragment(), DanhSachAdapter.OnItemClickListener {
    var DSList = mutableListOf<danhsachmodel>()
    private lateinit var themNVFragment: ThemNVFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        themNVFragment = ThemNVFragment()
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_danhsach_n_v, container, false)
        initRecyclerView(view)
        return view
    }

    private fun initRecyclerView(view: View?) {
        Log.e("danhsach", DSList.toString() )
        val revdanhsachnhanvien = view?.findViewById<RecyclerView>(R.id.rev_danhsachnhanvien)
        revdanhsachnhanvien?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = DanhSachAdapter(DSList,this@DanhsachNVFragment)
        }
        danhsach()
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
                    DSList.clear()
                    DSList.addAll(response.body()!!)
                    updateDS()
                }
            }
            override fun onFailure(call: Call<MutableList<danhsachmodel>>, t: Throwable) {
                t.printStackTrace()
                Log.e("SANPHAM3", t.message.toString())
            }
        })
    }

    private fun updateDS() {
        rev_danhsachnhanvien.adapter?.notifyDataSetChanged()
    }

    override fun onSUANVClick(item: danhsachmodel) {
        var bundle = Bundle().apply {
            putString("TENNV",item.TENNV)
            putString("SDT",item.SDT)
            putInt("MANV",item.MANV)
            putString("LUONG",item.LUONG)
        }
        themNVFragment.arguments = bundle
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fragment,themNVFragment)
        fragmentTransaction?.commit()
    }

    override fun onXOANVClick(item: danhsachmodel) {
        Log.e("ERROR", "onXOANVClick: OKKKKKKKKKKKKKKKKk")
        val xacnhan = AlertDialog.Builder(activity)
        xacnhan.setTitle("XÓA NHÂN VIÊN")
        xacnhan.setMessage("Bạn thật sự muốn xóa ak?")
        xacnhan.setPositiveButton("Xóa Gấp",DialogInterface.OnClickListener{
            dialog, which ->
            xoanhanvien(item.MANV)
            dialog.cancel()
        }
        )
        xacnhan.setNegativeButton("Nhầm thôi",DialogInterface.OnClickListener{
            dialog, which ->
            dialog.cancel()
        })
        val alert = xacnhan.create()
        alert.show()
    }

    private fun xoanhanvien(manv: Int) {
        val myurl = BIEN().local+"nhanvien.php?MANV=$manv"
        val queue = Volley.newRequestQueue(activity)
        val stringRequest = StringRequest(
            Request.Method.GET, myurl,
            {
                danhsach()
                Log.e("NHAPSP","sent OK! $myurl") },
            {Log.e("NHAPSP","sent Fail! $myurl")})
        queue.add(stringRequest)
    }
}