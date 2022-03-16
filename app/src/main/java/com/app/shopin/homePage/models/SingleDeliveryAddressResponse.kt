package com.app.shopin.homePage.models

class SingleDeliveryAddressResponse (
        val currentTimeStamp: String,
        val status: Boolean,
        val data: DeliveryAddressData?,
        val status_code: Int
    )
