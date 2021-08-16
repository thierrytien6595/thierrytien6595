package com.example.myapplication.variable

import android.util.Log
import com.example.myapplication.QUANLYHUYMON.APIService
import com.example.myapplication.QUANLYHUYMON.ServiceGenerator
import com.example.myapplication.QUANLYNHANVIEN.danhsachmodel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BIEN {
    private val local = "http://192.168.1.222/thach/"
    public fun url():String{
        return local
    }
}