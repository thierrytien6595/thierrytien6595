package com.example.retrofitexample.SANPHAM

import retrofit2.Call
import retrofit2.http.GET

interface SPService {
    @GET("sanpham.php")
    fun getPosts() :Call<MutableList<SanPhamModel>>
}