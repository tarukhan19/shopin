package com.app.shopin.homePage.models


data class AllStoreDataValues(
    var id: String?, var name: String?, var lattitude: String?,
    var longitude: String?, var store_image: String?, var contact_no: String?,
    var contact_email: String?, var rating: String?, var address: String?,
    val store_inventory: ArrayList<StoreInventoryData>?,val inventory_items: ArrayList<StoreInventoryData>?

)
