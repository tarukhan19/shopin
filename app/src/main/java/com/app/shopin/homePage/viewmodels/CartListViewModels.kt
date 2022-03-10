package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.homePage.models.CartListResponse
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.utils.OpenDialogBox
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartListViewModels : ViewModel() {
    var cartListViewModels: MutableLiveData<CartListResponse> = MutableLiveData()


    fun getObserveData(): MutableLiveData<CartListResponse> {
        return cartListViewModels
    }

    fun cartListResponse(requireContext: Context)
    {
        val request = ServiceBuilder.getApiService(requireContext)
        request.getCartList().enqueue(object :
            Callback<CartListResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<CartListResponse>,
                response: Response<CartListResponse>
            ) {
                if (response.isSuccessful) {
                    cartListViewModels.postValue(response.body())
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    var errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    cartListViewModels.postValue(null)
                    OpenDialogBox.openDialog(requireContext,"Error!", errorResponse!!.msg)

                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<CartListResponse>, t: Throwable) {
                cartListViewModels.postValue(null)
            }

        })

    }
}


