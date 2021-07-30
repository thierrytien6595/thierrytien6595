package com.example.myapplication.THUNHAP


import com.google.gson.annotations.SerializedName

data class quanlyItem(
    @SerializedName("DOANHTHU")
    val DOANHTHU: List<DOANHTHU>,
    @SerializedName("SOBAN_PV")
    val SOBANPV: String,
    @SerializedName("SOMON_CPV")
    val SOMONCPV: String,
    @SerializedName("SOMON_PV")
    val SOMONPV: String,
    @SerializedName("SOBAN_CPV")
    val SOBANCPV: String,
    @SerializedName("TONGTIEN")
    val TONGTIEN: String
)