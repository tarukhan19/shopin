package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.homePage.models.RemoveAddressResponse
import com.app.shopin.utils.OpenDialogBox
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoveAddressViewModel: ViewModel() {
    var removeAddressViewModel: MutableLiveData<RemoveAddressResponse> = MutableLiveData()

    fun getObserveData(): MutableLiveData<RemoveAddressResponse> {
        return removeAddressViewModel
    }

    fun removeAddressResponse(requireContext: Context, id:String) {

        val request = ServiceBuilder.getApiService(requireContext)
        request.removeAddress(id).enqueue(object :
            Callback<RemoveAddressResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<RemoveAddressResponse>,
                response: Response<RemoveAddressResponse>
            ) {
                if (response.isSuccessful) {
                    removeAddressViewModel.postValue(response.body())
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    var errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    removeAddressViewModel.postValue(null)
                    OpenDialogBox.openDialog(
                        requireContext,
                        "Error!",
                        errorResponse!!.msg,
                        ""
                    )

                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<RemoveAddressResponse>, t: Throwable) {
                removeAddressViewModel.postValue(null)
            }

        })

    }
}


