package com.app.shopin.autocompleteLib.listener

import com.app.shopin.autocompleteLib.model.PlaceDetails


/**
 * Created by mukeshsolanki on 28/02/19.
 */
interface OnPlacesDetailsListener {
  /**
   * Triggered when the places details are fetched and returns the details of the pace
   */
  fun onPlaceDetailsFetched(placeDetails: PlaceDetails)

  /**
   * Triggered when there is an error and passes the error message along as a parameter
   */
  fun onError(errorMessage: String)
}