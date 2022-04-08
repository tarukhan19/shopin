package com.app.shopin.Orders.models


data class OrderHistoryListDataValues(
    val id: String?,
    val order_no:String,
    val created_date: String?,
    val item_count: Int?,
    val order_status: String?,
    val order_item: ArrayList<OrderItemArrayList>,
    val updated_date: String?,
    )