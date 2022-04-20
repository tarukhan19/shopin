package com.app.shopin.utils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TimeDateConversion {
    companion object
    {
        suspend fun convertDate(sdate: String):String
        {
            var formatted: String = ""

            try {

                val input = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

                val output = SimpleDateFormat("MMM dd, EEE", Locale.getDefault())

                var d: Date? = null
                input.setTimeZone(TimeZone.getTimeZone("UTC"));
                d = input.parse(sdate)
                formatted = output.format(d)
                Log.e("formatted", formatted)

            } catch (e: ParseException) {
                e.printStackTrace()
                Log.e("exception", e.message.toString())

            }
            return formatted
        }
         fun convertTime(stime: String):String
        {
            var timeconvert=""
            try {
                val sdf = SimpleDateFormat("HH:mm:ss")
                val dateObj = sdf.parse(stime)
                timeconvert = SimpleDateFormat("KK:mm a").format(dateObj)
                Log.e("timeconvert", timeconvert.toString())
            }catch (e: ParseException) {
                e.printStackTrace()
                Log.e("exception", e.message.toString())

            }
            return timeconvert
        }
    }

}