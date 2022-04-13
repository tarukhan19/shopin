package com.app.shopin.Orders.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Orders.models.OrderHistoryListResponse
import com.app.shopin.Util.Utils
import com.customer.gogetme.Retrofit.ServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class OrderHistoryListViewModel : ViewModel() {
    private var orderHistoryListDataValues: MutableLiveData<OrderHistoryListResponse> = MutableLiveData()

    fun getAllStoreListObserver(): MutableLiveData<OrderHistoryListResponse> {
        return orderHistoryListDataValues
    }

    fun getAllOrderHistoryList(requireContext: Context) {

        val request = ServiceBuilder.getApiService(requireContext)


        CoroutineScope(Dispatchers.IO).launch {
            val response = request.getOrderList()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        orderHistoryListDataValues.postValue(response.body())
                    } else {
                        Utils.showToast("error", requireContext)
                    }
                } catch (e: HttpException) {
                    Utils.showToast("Exception ${e.message}", requireContext)

                } catch (e: Throwable) {
                    Utils.showToast("Something else went wrong", requireContext)

                }
            }
        }

//        request.getOrderList().enqueue(object : Callback<OrderHistoryListResponse> {
//            @SuppressLint("NullSafeMutableLiveData")
//            override fun onResponse(
//                call: Call<OrderHistoryListResponse>,
//                response: Response<OrderHistoryListResponse>
//            ) {
//                if (response.isSuccessful) {
//                    orderHistoryListDataValues.postValue(response.body())
//                } else {
//                    orderHistoryListDataValues.postValue(null)
//                }
//            }
//
//            @SuppressLint("NullSafeMutableLiveData")
//            override fun onFailure(call: Call<OrderHistoryListResponse>, t: Throwable) {
//                orderHistoryListDataValues.postValue(null)
//            }
//
//        })

    }
}
