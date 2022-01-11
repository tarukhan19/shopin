package com.app.shopin.model

import com.app.shopin.UserAuth.model.Data
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class EmailResponse(
@SerializedName("status_code") val status_code: Int,
@SerializedName("data") val data: JSONObject,
@SerializedName("currentTimeStamp") val currentTimeStamp: String,
@SerializedName("status") val status: Boolean
)