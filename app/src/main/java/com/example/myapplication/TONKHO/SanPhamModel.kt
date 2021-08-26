package com.example.myapplication.TONKHO
data class SanPhamModel (val TENSP:String,
                         val HINHSP:String,
                         val GIASP:String,
                         val MASP:String,
                         val SOLUONG:Int,
                         val MonPhu:Int?=0,
                         val MALOAISP:Int?=0
                         )