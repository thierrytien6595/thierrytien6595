package com.example.retrofitexample.SPDACHON

import com.google.gson.annotations.SerializedName

data class listmon(
    @SerializedName("TENBAN")
    val TENBAN:String,
    @SerializedName("jsondata")
    val jsondata:String
)
