package com.app.shopin.model

import com.app.shopin.UserAuth.model.OtpResponseItems
import com.google.gson.annotations.SerializedName
import org.json.JSONObject


data class OtpResponse(
    @SerializedName("status_code") val status_code: Int,
    @SerializedName("msg") val msg: String,
    @SerializedName("data") val otpResponseItems: OtpResponseItems,
    @SerializedName("currentTimeStamp") val currentTimeStamp: String?,
    @SerializedName("status") val status: Boolean?
)