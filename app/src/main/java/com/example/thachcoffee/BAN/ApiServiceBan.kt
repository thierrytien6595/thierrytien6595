package com.example.thachcoffee.BAN

import retrofit2.Call
import retrofit2.http.GET

interface ApiServiceBan {
    @GET("ban.php")
    fun getSP() :Call<MutableList<BanModel>>
}