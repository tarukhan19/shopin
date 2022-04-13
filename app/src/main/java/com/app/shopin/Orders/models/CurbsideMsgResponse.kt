package com.app.shopin.Orders.models

data class CurbsideMsgResponse (
    val currentTimeStamp: String,
    val status: Boolean,
    val status_code: Int,
    val msg: String
        )