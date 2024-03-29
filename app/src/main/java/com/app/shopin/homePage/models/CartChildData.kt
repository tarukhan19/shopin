package com.app.shopin.homePage.models


data class CartChildData(
    var id: String?,
    var inventory_name: String?,
    var order_type: String?,
    var price: Double,
    var inventory_image: String,
    var size_unit: String?,
    var quantity: String?,
    var size: String?,
    var tax_amount: String?,
    var stock: Int?,
    var store: String?,
    var inventory: String?,
    var cart_quatity: Int?,
    var total_amount: Double,
    var item_issue: Boolean
)
