package com.example.thachcoffee

import java.text.Normalizer
import java.util.regex.Pattern

class bien {
    val localhost="http://192.168.1.222/thach/"
    val imagelocal="http://192.168.1.222/thach/image/"
    fun convert(text:String):String{
        val temptext = Normalizer.normalize(text, Normalizer.Form.NFD)
        val pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        var s = pattern.matcher(temptext).replaceAll("")
        s = s.replace("Đ","D")
        s= s.replace("đ","d")
        return s
    }
}