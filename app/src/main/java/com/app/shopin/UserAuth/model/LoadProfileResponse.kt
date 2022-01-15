package com.app.shopin.UserAuth.model

import com.google.gson.annotations.SerializedName


data class LoadProfileResponse(
 @SerializedName("status_code") val status_code: Int,
@SerializedName("msg") val msg: String,
@SerializedName("data") val profileResponseItems: ProfileResponseItems,
@SerializedName("currentTimeStamp") val currentTimeStamp: String?,
@SerializedName("status") val status: Boolean?

    )

