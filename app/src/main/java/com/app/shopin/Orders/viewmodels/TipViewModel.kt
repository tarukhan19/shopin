package com.app.shopin.Orders.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Orders.models.TipResponse
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.utils.OpenDialogBox
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TipViewModel: ViewModel() {
    private var tipResponse: MutableLiveData<TipResponse> = MutableLiveData()

    fun tipObserver(): MutableLiveData<TipResponse> {
        return tipResponse
    }

    fun tipSubmission(requireContext: Context, storeid: String, orderid: String, comment: String,amount:String) {
//        val request = ServiceBuilder.getApiService(requireContext)
//        request.tipSubmit(storeid,orderid,comment,amount).enqueue(object : Callback<TipResponse> {
//            @SuppressLint("NullSafeMutableLiveData")
//            override fun onResponse(
//                call: Call<TipResponse>,
//                response: Response<TipResponse>
//            ) {
//                if (response.isSuccessful) {
//                    tipResponse.postValue(response.body())
//                } else {
//
//                    val gson = Gson()
//                    val type = object : TypeToken<ErrorResponse>() {}.type
//                    var errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
//                    tipResponse.postValue(null)
//                    OpenDialogBox.openDialog(
//                        requireContext,
//                        "Error!",
//                        errorResponse!!.msg,
//                        ""
//                    )
//
//                }
//            }
//
//            @SuppressLint("NullSafeMutableLiveData")
//            override fun onFailure(call: Call<TipResponse>, t: Throwable) {
//                tipResponse.postValue(null)
//            }
//
//        })

    }
}
