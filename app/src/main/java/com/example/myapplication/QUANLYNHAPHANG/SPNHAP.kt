package com.example.myapplication.QUANLYNHAPHANG


import com.google.gson.annotations.SerializedName

data class SPNHAP(
    @SerializedName("TENSP")
    val TENSP: String,
    @SerializedName("SOLUONG")
    val SOLUONG: String,
    @SerializedName("DONGIA")
    val DONGIA: String
    )