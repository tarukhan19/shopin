package com.app.shopin.homePage.models


data class CartListResponse (val currentTimeStamp: String,
                                       val status: Boolean,
                             val data: CartListData,

                                       val status_code: Int)