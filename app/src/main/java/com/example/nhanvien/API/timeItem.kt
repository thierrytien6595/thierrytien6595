package com.example.nhanvien.API


import com.google.gson.annotations.SerializedName

data class timeItem(
    @SerializedName("THU")
    val THU: String,
    @SerializedName("NGAY")
    val NGAY: String,
    @SerializedName("TIMEIN")
    val TIMEIN: String,
    @SerializedName("TIMEOUT")
    val TIMEOUT: String
)