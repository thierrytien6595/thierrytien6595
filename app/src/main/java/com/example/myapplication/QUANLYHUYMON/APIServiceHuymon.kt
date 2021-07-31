package com.example.myapplication.QUANLYHUYMON

import com.example.myapplication.THUNHAP.quanlyItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIServiceHuymon {
    @GET("chitiethuymon.php")
    fun chitiethuymon() : Call<MutableList<chitietdataItem>>

    @GET("quanlydata.php?")
    fun quanlydata(@Query("ID") ID:Int) : Call<MutableList<quanlyItem>>
}