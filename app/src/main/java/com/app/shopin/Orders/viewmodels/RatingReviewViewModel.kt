package com.app.shopin.Orders.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Orders.models.RatingReviewResponse
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.utils.OpenDialogBox
import com.bumptech.glide.util.Util
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class RatingReviewViewModel : ViewModel() {
    private var ratingReviewResponse: MutableLiveData<RatingReviewResponse> = MutableLiveData()

    fun getRatingReviewObserver(): MutableLiveData<RatingReviewResponse> {
        return ratingReviewResponse
    }

    fun ratingReviewSubmission(
        requireContext: Context,
        storeid: String,
        orderid: String,
        rating: String,
        review: String
    ) {


        val service = ServiceBuilder.getApiService(requireContext)

//        CoroutineScope(Dispatchers.IO).launch {
//            val response = service.ratingReviewSubmit(storeid, orderid, rating, review)
//            withContext(Dispatchers.Main) {
//                try {
//                    if (response.isSuccessful) {
//                        ratingReviewResponse.postValue(response.body())
//                    } else {
//                        Utils.showToast("error", requireContext)
//                    }
//                } catch (e: HttpException) {
//                    Utils.showToast("Exception ${e.message}", requireContext)
//
//                } catch (e: Throwable) {
//                    Utils.showToast("Something else went wrong", requireContext)
//
//                }
//            }
//        }
    }
}





