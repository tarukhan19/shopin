package com.app.shopin.utils

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.RetryPolicy
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.app.shopin.customview.RegularTextView
import org.json.JSONException
import org.json.JSONObject

class DistanceCalculationMethod {
    companion object
    {

       lateinit var queue: RequestQueue
        var distance:String=""


        fun getDistance(
            lat1: Double,
            lng1: Double,
            lat: Double,
            lng: Double,
            context: Context?,
            distanceTV: RegularTextView
        )
        {
            queue = Volley.newRequestQueue(context)
            val uri =
                "https://maps.googleapis.com/maps/api/distancematrix/json?origins=$lat,$lng&destinations=$lat1,$lng1&mode=DRIVING&key=AIzaSyAdoq9IbV9WMAc_wAyEpNZslrPUmJ_RCLg"
            val postRequest: StringRequest = object : StringRequest(
                Method.GET, uri,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val rowArray = jsonObject.getJSONArray("rows")
                        val rowJsonObj = rowArray.getJSONObject(0)
                        val elementsArray = rowJsonObj.getJSONArray("elements")
                        val elementJsonObj = elementsArray.getJSONObject(0)
                        val distanceJsonObj = elementJsonObj.getJSONObject("distance")
                        distance = distanceJsonObj.getString("text")
                        distanceTV.setText(distance)

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }

                },
                Response.ErrorListener { error -> error.printStackTrace() }
            )
            {

            }
            queue.add<String>(postRequest)
        }
    }
}