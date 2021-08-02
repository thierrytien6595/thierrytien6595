package com.example.myapplication.QUANLYNHAPHANG


import com.example.myapplication.QUANLYHUYMON.SP
import com.google.gson.annotations.SerializedName

data class chitietdataItemNhap(
    @SerializedName("ID")
    val ID: String,
    @SerializedName("TENNV")
    val TENNV: String,
    @SerializedName("TIME")
    val TIME: String,
    @SerializedName("TONGTIEN")
    val TONGTIEN: String,
    @SerializedName("SPNHAP")
    val SPNHAP: List<SPNHAP>
)