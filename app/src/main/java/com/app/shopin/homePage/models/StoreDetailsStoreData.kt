package com.app.shopin.homePage.models

import com.app.shopin.Orders.models.StoreTimeValues

data class StoreDetailsStoreData(
    val store: StoreDetailsData,
    val category: ArrayList<StoreCategoryData>,
    val store_timmings: StoreTimeValues
)