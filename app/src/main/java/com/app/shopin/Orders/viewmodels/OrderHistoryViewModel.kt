package com.app.shopin.Orders.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Orders.models.OrderHistoryDetailsResponse
import com.customer.gogetme.Retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderHistoryViewModel : ViewModel() {
    private var orderHistoryListDataValues: MutableLiveData<OrderHistoryDetailsResponse> = MutableLiveData()

    fun getAllStoreListObserver(): MutableLiveData<OrderHistoryDetailsResponse> {
        return orderHistoryListDataValues
    }

    fun getAllOrderHistoryList(requireContext: Context,order_id:String) {
        val request = ServiceBuilder.getApiService(requireContext)
        request.getOrderDetails(order_id).enqueue(object : Callback<OrderHistoryDetailsResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<OrderHistoryDetailsResponse>,
                response: Response<OrderHistoryDetailsResponse>
            ) {
                if (response.isSuccessful) {
                    orderHistoryListDataValues.postValue(response.body())
                } else {
                    orderHistoryListDataValues.postValue(null)
                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<OrderHistoryDetailsResponse>, t: Throwable) {
                orderHistoryListDataValues.postValue(null)
            }

        })

    }
}
