package com.app.shopin.homePage.models

import com.google.gson.annotations.SerializedName
import org.json.JSONObject


data class CartListResponse (@SerializedName("currentTimeStamp") val currentTimeStamp: String,
                             @SerializedName("status_code") val status_code: Int,
                             @SerializedName("status") val status: String?,
                             @SerializedName("data") val data: CartListData?)

