package com.app.shopin.homePage.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.homePage.models.SingleDeliveryAddressResponse
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadSingleAddressViewModel : ViewModel() {
    private var loadSingleAddressViewModel: MutableLiveData<SingleDeliveryAddressResponse?> = MutableLiveData()

    fun getObserve(): MutableLiveData<SingleDeliveryAddressResponse?> {
        return loadSingleAddressViewModel
    }

    fun makeLoadAddressApiCall(context: Context, id: String) {
        val retIn = ServiceBuilder.getApiService(context)
        retIn.getSingleDeliveryAddress(id).enqueue(object : Callback<SingleDeliveryAddressResponse> {
            override fun onResponse(call: Call<SingleDeliveryAddressResponse>, response: Response<SingleDeliveryAddressResponse>) {
                if (response.isSuccessful) {
                    loadSingleAddressViewModel.postValue(response.body())
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    val errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    Utils.showToast(errorResponse!!.msg,context)
                    loadSingleAddressViewModel.postValue(null)
                }
            }
            override fun onFailure(call: Call<SingleDeliveryAddressResponse>, t: Throwable) {
                loadSingleAddressViewModel.postValue(null)
            }
        })
    }

}