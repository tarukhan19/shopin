package com.app.shopin.homePage.models

data class DeliveryAddressData(
    val id:String?,
    val address: String?,
    val name: String?,
    val apartment_building: String?,
    val delivery_instruction: String,
    val lattitude: Double,
    val longitude: Double,
    val address_type:String?,
    val is_default: Boolean?


    )