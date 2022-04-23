package com.app.shopin.homePage.models


data class StoreCategoryArrayListData(
    val category: ArrayList<StoreCategoryData>?,

    val store: ArrayList<AllStoreDataValues>?,
    val store_product: ArrayList<AllStoreDataValues>?,

    val stores: ArrayList<AllStoreDataValues>?,
    val stores_product: ArrayList<AllStoreDataValues>?

)