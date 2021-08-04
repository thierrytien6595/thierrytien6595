package com.example.myapplication.QUANLYHUYMON

import com.example.myapplication.QUANLYNHANVIEN.danhsachmodel
import com.example.myapplication.QUANLYNHAPHANG.chitietdataItemNhap
import com.example.myapplication.THUNHAP.quanlyItem
import com.example.myapplication.TONKHO.SanPhamModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {
    @GET("chitiethuymon.php")
    fun chitiethuymon() : Call<MutableList<chitietdataItem>>

    @GET("chitietnhaphang.php")
    fun chitietnhaphang() : Call<MutableList<chitietdataItemNhap>>

    @GET("quanlydata.php?")
    fun quanlydata(@Query("ID") ID:Int) : Call<MutableList<quanlyItem>>

    @GET("sanpham.php")
    fun getSP() :Call<MutableList<SanPhamModel>>

    @GET("sanpham.php?")
    fun editSP(@Query("MASP") MASP:String, @Query("EDIT") EDIT:Int) :Call<MutableList<SanPhamModel>>

    @GET("danhsachnhanvien.php")
    fun danhsachnhanvien() : Call<MutableList<danhsachmodel>>

}