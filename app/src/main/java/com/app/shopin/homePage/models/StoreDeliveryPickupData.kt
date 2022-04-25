package com.app.shopin.homePage.models

data class StoreDeliveryPickupData(
    val opening_time: String,
    val closing_time: String,
    val delivery_estimate_time: String,
    val pickup_estimate_time: String,
    val min_free_delivery_amount: Double
)