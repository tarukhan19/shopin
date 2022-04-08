package com.app.shopin.Orders.models


data class RatingReviewResponse (

    val currentTimeStamp: String,
    val data: OrderHistoryArrayListData,
    val status: Boolean,
    val status_code: Int

)