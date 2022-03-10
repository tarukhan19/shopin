package com.app.shopin.homePage.models


data class CartListResponse (val currentTimeStamp: String,
                                       val status: Boolean,
                             val data: StoreCategoryArrayListData,

                                       val status_code: Int)