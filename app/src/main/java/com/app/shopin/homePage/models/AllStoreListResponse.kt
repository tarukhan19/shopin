package com.app.shopin.homePage.models

data class AllStoreListResponse
    (
    val currentTimeStamp: String,
    val data: AllStoreArrayListData,
    val status: Boolean,
    val status_code: Int
)
