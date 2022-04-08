package com.app.shopin.Orders.models

data class OrderHistoryDetailsResponse (

    val currentTimeStamp: String,
    val status: Boolean,
    val data: OrderHistoryData,
    val status_code: Int
        )