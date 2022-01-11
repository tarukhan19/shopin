package com.app.shopin.UserAuth.model

data class OtpResponseItems (
    val email: String,
    val phone_no: String,
    val token: String,
    val user_id: String,
    val username: String

        )