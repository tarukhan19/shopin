package com.app.shopin.Orders.models

import com.app.shopin.homePage.models.IssuesData


data class IssueWithItemValues(
    var id: String?, var store_name: String?, var latitude: String?,
    var longitude: String?, var store_image: String?, var contact_no: String?, var return_policy:String?,
    var contact_email: String?, var rating: String?, var address: String?, var cart_quantity:String?,
    val order_item: ArrayList<IssueWithItemProdData>?,val issues_keys: ArrayList<IssuesData>?,
    val store_rating:StoreratingData
)