package com.app.shopin.homePage.models


data class DeliveryAddressListResponse(
    val currentTimeStamp: String,
    val data: DeliveryAddressArrayListData,
    val status: Boolean,
    val status_code: Int
)