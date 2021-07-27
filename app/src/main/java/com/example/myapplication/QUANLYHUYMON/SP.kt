package com.example.myapplication.QUANLYHUYMON


import com.google.gson.annotations.SerializedName

data class SP(
    @SerializedName("TENSP")
    val TENSP: String,
    @SerializedName("SOLUONG")
    val SOLUONG: String
)