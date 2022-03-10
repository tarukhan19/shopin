package com.app.shopin.homePage.models

data class AddToCartResponse (
    val currentTimeStamp: String,
    val data: AllStoreArrayListData,
    val status: Boolean,
    val status_code: Int
)