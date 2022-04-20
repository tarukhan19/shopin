package com.app.shopin.Orders.models

data class SendSelectedIssue (
    val items:ArrayList<SelectedIssue>,
    val order_no:String
        )