package com.example.myapplication.QUANLYHUYMON

import retrofit2.Call
import retrofit2.http.GET

interface APIServiceHuymon {
    @GET("chitiethuymon.php")
    fun chitiethuymon() : Call<MutableList<chitietdataItem>>
}