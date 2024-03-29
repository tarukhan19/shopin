package com.app.shopin.Orders.models

import com.app.shopin.homePage.models.CartChildData
import org.json.JSONObject

data class OrderHistoryDetail (
    val store:String?,
    val order_no: String?,
    val created_date: String?,
    val store_total_amount:String?,
    val order_status:String,
    val store_order_type:String,
    val store_name:String,
    val store_image:String,
    val rating:String,
    val review:String,
    val myrating:String,
    val order_item: ArrayList<CartChildData>?,
    val store_rating:StoreratingData?,
    val order_tip:Ordertipdata?
    )

  //  val store_order:ArrayList<StoreHistoryDetails>)