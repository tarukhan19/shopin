package com.app.shopin.Orders.models

data class CreateIssueResponse(
    val currentTimeStamp: String,
    val status: Boolean,
    val status_code: Int
)