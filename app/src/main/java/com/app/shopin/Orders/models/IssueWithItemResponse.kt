package com.app.shopin.Orders.models

data class IssueWithItemResponse(
    val currentTimeStamp: String,
    val status: Boolean,
    val data: IssueWithItemData,
    val status_code: Int
)