package com.app.shopin.Orders.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Orders.models.RatingReviewResponse
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.utils.OpenDialogBox
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RatingReviewViewModel: ViewModel() {
    private var ratingReviewResponse: MutableLiveData<RatingReviewResponse> = MutableLiveData()

    fun getRatingReviewObserver(): MutableLiveData<RatingReviewResponse> {
        return ratingReviewResponse
    }

    fun ratingReviewSubmission(requireContext: Context, id: String, rating: String, review: String) {
        val request = ServiceBuilder.getApiService(requireContext)
        request.ratingReviewSubmit(id,rating,review).enqueue(object : Callback<RatingReviewResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<RatingReviewResponse>,
                response: Response<RatingReviewResponse>
            ) {
                Utils.showToast(response.isSuccessful.toString(),requireContext)
                if (response.isSuccessful) {
                    Utils.showToast("Rating given successfully",requireContext)
                    ratingReviewResponse.postValue(response.body())
                } else {

                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    var errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    ratingReviewResponse.postValue(null)
                    OpenDialogBox.openDialog(
                        requireContext,
                        "Error!",
                        errorResponse!!.msg,
                        ""
                    )

                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<RatingReviewResponse>, t: Throwable) {
                ratingReviewResponse.postValue(null)
            }

        })

    }
}
