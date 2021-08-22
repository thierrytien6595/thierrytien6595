package com.example.myapplication.QUANLYNHANVIEN

import com.google.gson.annotations.SerializedName

data class lichsunhanvienitem(@SerializedName("TENNV")
                              val TENNV: String,
                              @SerializedName("THU")
                              val THU: String,
                              @SerializedName("NGAY")
                              val NGAY: String,
                              @SerializedName("TIMEIN")
                              val TIMEIN: String,
                              @SerializedName("TIMEOUT")
                              val TIMEOUT: String,
                              @SerializedName("WORKTIME")
                              val WORKTIME: String,
                              @SerializedName("DIEMTRU")
                              val DIEMTRU: String)
