package com.app.shopin.Orders.models

data class IssueWithItemProdData(
    var id: String?,
    var inventory_name: String?,
    var order_type: String?,
    var price: Double,
    var size_unit: String?,
    var quantity: String?,
    var size: String?,
    var tax_amount: String?,
    var stock_quantity: Int?,
    var store: String?,
    var inventory: String?,
    var cart_quatity: Int?,
    var total_amount: Double?,
    var item_issue:Boolean,
    var issue_name:String
)
