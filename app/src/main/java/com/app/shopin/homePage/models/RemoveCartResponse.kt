package com.app.shopin.homePage.models


data class RemoveCartResponse (
    val currentTimeStamp: String,
    val data: AllStoreArrayListData,
    val status: Boolean,
    val status_code: Int
)