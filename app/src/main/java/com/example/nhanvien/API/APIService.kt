package com.example.nhanvien.API

import com.example.nhanvien.danhsachmodel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("chamcong.php?")
    fun chamcong(@Query("tennv") tennv:String) : Call<MutableList<timeItem>>
    @GET("danhsachnhanvien.php")
    fun danhsachnhanvien() : Call<MutableList<danhsachmodel>>
}