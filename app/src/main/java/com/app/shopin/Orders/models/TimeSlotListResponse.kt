package com.app.shopin.Orders.models

data class TimeSlotListResponse(

    val currentTimeStamp: String,
    val data: TimeListArrayListData,
    val status: Boolean,
    val status_code: Int

)