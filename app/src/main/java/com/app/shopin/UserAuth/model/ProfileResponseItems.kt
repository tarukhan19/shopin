package com.app.shopin.UserAuth.model

import com.google.gson.annotations.SerializedName

data class ProfileResponseItems(
    @SerializedName("user_profile") val user_profile: UserProfileData,

    )