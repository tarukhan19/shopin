package com.app.shopin.homePage.models

data class ProductDetailResponse  (val currentTimeStamp: String,
                                      val status: Boolean,
                                        val data:InventryItemData,
                                      val status_code: Int)