package com.app.shopin.homePage.models


data class StoreItemsData(
    var id: String?,
    var name: String?,
    var price: String?,
    var inventory_image: ArrayList<Inventory_Image_Data>,
    var size_unit: String?,
    var size: String?,
    var tax_amount: String?,
    var stock_quantity: Int?,
    var store: String?,
    var cart_quatity: Int?,
    var description: String?,
    var return_policy: String?
)