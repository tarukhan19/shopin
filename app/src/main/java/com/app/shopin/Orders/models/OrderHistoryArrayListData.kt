package com.app.shopin.Orders.models

import com.app.shopin.homePage.models.StatusListDataValues


data class OrderHistoryArrayListData(
    val order_list: ArrayList<OrderHistoryListDataValues>?,
    val status_list:ArrayList<StatusListDataValues>
)