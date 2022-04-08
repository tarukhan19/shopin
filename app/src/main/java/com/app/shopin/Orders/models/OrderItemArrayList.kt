package com.app.shopin.Orders.models

data class OrderItemArrayList (
    val id: String?,
    val inventory_image:String,
    val quantity:String,
    val inventory_name:String,
    val order_item: ArrayList<OrderItemArrayList>,

    )