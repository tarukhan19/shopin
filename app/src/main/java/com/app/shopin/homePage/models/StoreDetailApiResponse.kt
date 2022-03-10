package com.app.shopin.homePage.models

data class StoreDetailApiResponse (
    val currentTimeStamp: String,
    val data: StoreDetailsStoreData,
    val status: Boolean,
    val status_code: Int
)