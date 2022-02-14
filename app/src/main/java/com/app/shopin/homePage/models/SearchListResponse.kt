package com.app.shopin.homePage.models

data class SearchListResponse(
    val currentTimeStamp: String,
    val data: StoreCategoryArrayListData,
    val status: Boolean,
    val status_code: Int
)