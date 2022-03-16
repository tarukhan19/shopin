package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.homePage.models.RemoveCartResponse
import com.app.shopin.utils.OpenDialogBox
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoveCartViewModel: ViewModel() {
    var removecartviewmodel: MutableLiveData<RemoveCartResponse> = MutableLiveData()

    fun getObserveData(): MutableLiveData<RemoveCartResponse> {
        return removecartviewmodel
    }

    fun removeCartResponse(requireContext: Context,id:String) {

        val request = ServiceBuilder.getApiService(requireContext)
        request.removeCart(id).enqueue(object :
            Callback<RemoveCartResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<RemoveCartResponse>,
                response: Response<RemoveCartResponse>
            ) {
                if (response.isSuccessful) {
                    removecartviewmodel.postValue(response.body())
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    var errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    removecartviewmodel.postValue(null)
                    OpenDialogBox.openDialog(
                        requireContext,
                        "Error!",
                        errorResponse!!.msg,
                        ""
                    )

                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<RemoveCartResponse>, t: Throwable) {
                removecartviewmodel.postValue(null)
            }

        })

    }
}


