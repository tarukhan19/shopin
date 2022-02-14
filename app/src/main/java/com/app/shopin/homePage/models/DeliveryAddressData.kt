package com.app.shopin.homePage.models

data class DeliveryAddressData(
    val address: String?,
    val name: String?,
    val apartment_building: String?,
    val delivery_instruction: String,
    val lattitude: String?,
    val longitude: String?,
    val address_type:String?,
    val is_default: Boolean?


    )