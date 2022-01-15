package com.app.shopin.UserAuth.model

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class EditProfileResponse(
    @SerializedName("status_code") val status_code: Int,
    @SerializedName("data") val data: JSONObject?,
    @SerializedName("currentTimeStamp") val currentTimeStamp: String?,
    @SerializedName("status") val status: Boolean?
)
