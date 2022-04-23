package com.app.shopin.homePage.models

import com.app.shopin.Orders.models.StoreratingData


data class AllStoreDataValues(
    var id: String?, var name: String?, var latitude: String?,
    var longitude: String?, var store_image: String?, var contact_no: String?,var return_policy:String?,
    var contact_email: String?, var rating: String?, var address: String?,var cart_quantity:String?,
    val store_inventory: ArrayList<StoreInventoryData>?, val cart_item: ArrayList<StoreInventoryData>?,
    val inventory_items: ArrayList<StoreInventoryData>?,val order_item: ArrayList<StoreInventoryData>?,
    val storeratereview:StoreratingData,val business_type:Boolean,    val rating_details:StoreratingData


)