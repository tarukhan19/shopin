package com.app.shopin.homePage.models

import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import org.json.JSONObject

data class PlaceOrderResponse(
    @SerializedName("msg") val msg: String,
    @SerializedName("status_code") val status_code: Int,
    @SerializedName("status") val status: String?

)

