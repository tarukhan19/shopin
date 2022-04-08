package com.app.shopin.Orders.models

import com.app.shopin.homePage.models.CartChildData

data class StoreHistoryDetails (
    val order_status:String,
    val store_order_type:String,
    val store_name:String,
    val store_image:String,
    val rating:String,
    val review:String,
    val myrating:String
    , val order_item: ArrayList<CartChildData>?

    )