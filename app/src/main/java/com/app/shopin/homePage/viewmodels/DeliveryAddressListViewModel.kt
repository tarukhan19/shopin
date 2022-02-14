package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.homePage.Adapter.DeliveryAddressAdapter
import com.app.shopin.homePage.models.DeliveryAddressData
import com.app.shopin.homePage.models.DeliveryAddressListResponse
import com.customer.gogetme.Retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DeliveryAddressListViewModel : ViewModel() {
    private var deliveryAddressListLiveData: MutableLiveData<DeliveryAddressListResponse> = MutableLiveData()
    private var deliveryAdapter: DeliveryAddressAdapter = DeliveryAddressAdapter()

    fun getDeliveryAddressObserver(): MutableLiveData<DeliveryAddressListResponse> {
        return deliveryAddressListLiveData
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAdapter(deliveryAddressListData: ArrayList<DeliveryAddressData>) {
        deliveryAdapter.setListData(deliveryAddressListData)
        deliveryAdapter.notifyDataSetChanged()
    }

    fun getAdapter(): DeliveryAddressAdapter {
        return deliveryAdapter
    }

    fun getDeliveryAddressList(requireContext: Context) {

        val request = ServiceBuilder.getApiService(requireContext)
        request.getDeliveryAddressList().enqueue(object : Callback<DeliveryAddressListResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<DeliveryAddressListResponse>,
                response: Response<DeliveryAddressListResponse>
            ) {
                if (response.isSuccessful) {
                    deliveryAddressListLiveData.postValue(response.body())
                } else {
                    deliveryAddressListLiveData.postValue(null)
                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<DeliveryAddressListResponse>, t: Throwable) {
                deliveryAddressListLiveData.postValue(null)
            }

        })

    }
}
