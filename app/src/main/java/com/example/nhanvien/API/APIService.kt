package com.example.nhanvien.API

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("chamcong.php?")
    fun chamcong(@Query("manv") manv:Int) : Call<MutableList<timeItem>>
}