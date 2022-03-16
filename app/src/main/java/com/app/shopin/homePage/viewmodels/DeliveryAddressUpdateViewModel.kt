package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.homePage.models.DeliveryAddressUpdateResponse
import com.customer.gogetme.Retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeliveryAddressUpdateViewModel : ViewModel() {
    private var deliveryAddressAddLiveData: MutableLiveData<DeliveryAddressUpdateResponse> = MutableLiveData()


    fun getObserveData(): MutableLiveData<DeliveryAddressUpdateResponse> {
        return deliveryAddressAddLiveData
    }

    fun getDeliveryAddressUpdateResp(requireContext: Context,id:String, name: String, address: String, addresstype: String
                                  , deliveryinstruction: String, floor: String,
                                  isDefault: String, lat : Double, lng : Double) {

        val request = ServiceBuilder.getApiService(requireContext)
        request.updateDeliveryAddress(id,name,address,addresstype,deliveryinstruction, floor,isDefault,lat,lng ).enqueue(object :
            Callback<DeliveryAddressUpdateResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<DeliveryAddressUpdateResponse>,
                response: Response<DeliveryAddressUpdateResponse>
            ) {
                if (response.isSuccessful) {
                    deliveryAddressAddLiveData.postValue(response.body())
                } else {
                    deliveryAddressAddLiveData.postValue(null)
                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<DeliveryAddressUpdateResponse>, t: Throwable) {
                deliveryAddressAddLiveData.postValue(null)

            }

        })

    }



}
