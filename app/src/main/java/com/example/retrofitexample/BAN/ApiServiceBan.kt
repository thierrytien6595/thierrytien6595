package com.example.retrofitexample.BAN

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceBan {
    @GET("ban.php")
    fun getSP() :Call<MutableList<BanModel>>
}