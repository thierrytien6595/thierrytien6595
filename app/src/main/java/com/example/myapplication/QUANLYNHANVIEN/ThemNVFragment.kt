package com.example.myapplication.QUANLYNHANVIEN

import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_them_n_v.*

class ThemNVFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_them_n_v, container, false)
        if (this.arguments?.isEmpty == false) {
            var item = this.arguments
            val MANV = item?.getInt("MANV")
            val TENNV = item?.getString("TENNV")
            val LUONG = item?.getString("LUONG")
            val SDT = item?.getString("SDT")
            val title:TextView = view.findViewById(R.id.title1)
            val tennhanvien:EditText = view.findViewById(R.id.tennhanvien)
            val luongnhanvien:EditText= view.findViewById(R.id.luongnhanvien)
            val sdt:EditText = view.findViewById(R.id.sdt)
            val manv:TextView = view.findViewById(R.id.manv)
            manv.text = MANV.toString()
            title.text = "THAY ĐỔI THÔNG TIN"
            sdt.setText(SDT)
            tennhanvien.setText(TENNV)
            luongnhanvien.setText(LUONG)
        }
        // Inflate the layout for this fragment
        return view
    }

}