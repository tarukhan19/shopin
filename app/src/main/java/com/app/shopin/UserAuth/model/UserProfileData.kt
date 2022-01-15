package com.app.shopin.UserAuth.model

import com.google.gson.annotations.SerializedName

data class UserProfileData (
    @SerializedName("name") val name: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("phone_no") val phone_no: String?,
    @SerializedName("profile_img") val profile_img: String?
    )
