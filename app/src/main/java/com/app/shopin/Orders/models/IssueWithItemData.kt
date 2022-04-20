package com.app.shopin.Orders.models


data class IssueWithItemData (
    val order_detail: IssueWithItemValues,
    val store_timmings:StoreTimeValues
    )