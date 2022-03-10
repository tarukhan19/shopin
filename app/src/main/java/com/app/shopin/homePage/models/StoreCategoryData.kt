package com.app.shopin.homePage.models


data class StoreCategoryData(
    val id: Int?,
    val key: String?,
    val status: Int?,
    val value: String?,
    val image: String?,
    val inventory_items: ArrayList<StoreInventoryData>?,
)