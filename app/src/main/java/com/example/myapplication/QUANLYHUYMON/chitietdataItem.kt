package com.example.myapplication.QUANLYHUYMON


import com.google.gson.annotations.SerializedName

data class chitietdataItem(
    @SerializedName("ID")
    val ID: String,
    @SerializedName("LYDO")
    val LYDO: String,
    @SerializedName("TENNV")
    val TENNV: String,
    @SerializedName("SP")
    val SP: List<SP>,
    @SerializedName("TIME")
    val TIME: String
)