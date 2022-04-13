package com.app.shopin.Orders.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Orders.models.OrderHistoryDetailsResponse
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.utils.OpenDialogBox
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

import retrofit2.HttpException
import java.lang.Exception

class OrderHistoryViewModel : ViewModel() {
    private var orderHistoryListDataValues: MutableLiveData<OrderHistoryDetailsResponse?> = MutableLiveData()

    fun getAllStoreListObserver(): MutableLiveData<OrderHistoryDetailsResponse?> {
        return orderHistoryListDataValues
    }

    fun getAllOrderHistoryList(requireContext: Context,order_id:String) {


        val service = ServiceBuilder.getApiService(requireContext)

//        CoroutineScope(Dispatchers.IO).launch {
//            val response = service.getOrderDetails(order_id)
//            withContext(Dispatchers.Main) {
//                try {
//                    if (response.isSuccessful) {
//                        orderHistoryListDataValues.postValue(response.body())
//
//                    } else {
//                        try {
//                            val gson = Gson()
//                            val type = object : TypeToken<ErrorResponse>() {}.type
//                            var errorResponse: ErrorResponse? =
//                                gson.fromJson(response.errorBody()!!.charStream(), type)
//                            orderHistoryListDataValues.postValue(null)
//                            OpenDialogBox.openDialog(
//                                requireContext,
//                                "Error!",
//                                errorResponse!!.msg,
//                                ""
//                            )
//                        } catch (e: Exception) {
//                        }
//                    }
//                } catch (e: HttpException) {
//                    Utils.showToast("Exception ${e.message}", requireContext)
//
//                } catch (e: Throwable) {
//                    Utils.showToast("Something else went wrong", requireContext)
//
//                }
//            }
//        }


    }
}
