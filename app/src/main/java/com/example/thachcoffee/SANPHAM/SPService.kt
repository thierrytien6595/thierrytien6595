package com.example.thachcoffee.SANPHAM

import com.example.thachcoffee.SPDACHON.SPDaChonModel
import com.example.thachcoffee.SPDACHON.listmon
import retrofit2.Call
import retrofit2.http.*

interface SPService {
    @GET("sanpham.php")
    fun getSP() :Call<MutableList<SanPhamModel>>


    @GET("sanphamdachon.php?")
    fun getspdachon(@Query("TENBAN") tenban: String) :Call<MutableList<SPDaChonModel>>

    @FormUrlEncoded
    @POST("xoamon.php")
    fun xoamon(
        @Field("TENBAN")
        TENBAN:String,
        @Field("jsondata")
        jsondata:String
    ) : Call<MutableList<listmon>>

    @FormUrlEncoded
    @POST("add_bill.php")
    fun insertbill(
            @Field("TENBAN")
            TENBAN:String,
            @Field("jsondata")
            jsondata:String
    ) : Call<MutableList<listmon>>


}