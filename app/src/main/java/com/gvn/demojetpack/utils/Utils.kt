package com.gvn.demojetpack.utils

import java.text.SimpleDateFormat

class Utils {

    companion object {
        @JvmStatic
        fun formatDate(date: String): String {
            val df1 = SimpleDateFormat("ddMMyyyy")
            val df2 = SimpleDateFormat("dd/MM/yyyy")
            val dt = df1.parse(date)
            return df2.format(dt)
        }
    }


}