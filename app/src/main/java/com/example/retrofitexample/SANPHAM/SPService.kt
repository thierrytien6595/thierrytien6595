package com.example.retrofitexample.SANPHAM

import com.example.retrofitexample.SPDACHON.listmon
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface SPService {
    @GET("sanpham.php")
    fun getPosts() :Call<MutableList<SanPhamModel>>

    @FormUrlEncoded
    @POST("add_bill.php")
    fun insertbill(
            @Field("TENBAN")
            TENBAN:String,
            @Field("jsondata")
            jsondata:String
    ) : Call<MutableList<listmon>>
}