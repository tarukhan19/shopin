package com.app.shopin.autocompleteLib.model

/**
 * Created by mukeshsolanki on 28/02/19.
 */
data class PlaceDetails(
  val name: String,
  val address: ArrayList<Address>,
  val lat: Double,
  val lng: Double,
  val placeId: String,
  val url: String,
  val utcOffset: Int,
  val vicinity: String,
  val compoundPlusCode: String,
  val globalPlusCode: String
)