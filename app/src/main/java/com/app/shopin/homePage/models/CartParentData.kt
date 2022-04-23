package com.app.shopin.homePage.models

import com.app.shopin.Orders.models.StoreTimeValues

data class CartParentData(
    var id: String?,
    var name: String?,
    var lattitude: String?,
    var longitude: String?,
    var store_image: String?,
    var contact_no: String?,
    var contact_email: String?,
    var rating: String?,
    var address: String?,
    var cart_quantity: String?,
    val cart_item: ArrayList<CartChildData>?,


    var store_total_amount: Float?,
    var min_order_amount: String,
    val store_timmings: StoreTimeValues

)