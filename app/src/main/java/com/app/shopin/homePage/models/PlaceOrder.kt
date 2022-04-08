package com.app.shopin.homePage.models

import com.google.gson.annotations.SerializedName
import org.bouncycastle.util.Store

data class PlaceOrder(

    @SerializedName("order_type") val order_type: Int?,
    @SerializedName("tax_percentage") val tax_percentage: Int?,
//    @SerializedName("total_amount") val total_amount: Int?,
    @SerializedName("delivery_address") val delivery_address: Int?,
    @SerializedName("payment_method") val payment_method: Int?,
    @SerializedName("pikcup_comment") val pikcup_comment: String?,
    @SerializedName("tax_value") val tax_value: Int?,
    @SerializedName("pickup_date") val pickup_date: String?,
    @SerializedName("pickup_time_slot") val pickup_time_slot: String?,
    @SerializedName("store") val store: ArrayList<CartParentData>

)