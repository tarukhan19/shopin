package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.models.DeliveryAddressAddResponse
import com.customer.gogetme.Retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class DeliveryAddressAddViewModel : ViewModel() {
    private var deliveryAddressAddLiveData: MutableLiveData<DeliveryAddressAddResponse> = MutableLiveData()


    fun getObserveData(): MutableLiveData<DeliveryAddressAddResponse> {
        return deliveryAddressAddLiveData
    }

    fun getDeliveryAddressAddResp(requireContext: Context,name: String,address: String,addresstype: String
                                  ,deliveryinstruction: String,floor: String,
                                  isDefault: String, lat : Double,lng : Double) {

        val request = ServiceBuilder.getApiService(requireContext)
        request.addDeliveryAddress(name,address,addresstype,deliveryinstruction, floor,isDefault, lat,lng ).enqueue(object : Callback<DeliveryAddressAddResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<DeliveryAddressAddResponse>,
                response: Response<DeliveryAddressAddResponse>
            ) {
                if (response.isSuccessful) {
                    deliveryAddressAddLiveData.postValue(response.body())
                } else {
                    deliveryAddressAddLiveData.postValue(null)
                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<DeliveryAddressAddResponse>, t: Throwable) {
                deliveryAddressAddLiveData.postValue(null)
            }

        })

    }



}
