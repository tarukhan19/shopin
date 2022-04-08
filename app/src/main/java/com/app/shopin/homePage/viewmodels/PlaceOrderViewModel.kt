package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.models.PlaceOrder
import com.app.shopin.homePage.models.PlaceOrderResponse
import com.customer.gogetme.Retrofit.ServiceBuilder
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class PlaceOrderViewModel : ViewModel() {
    var placeOrderViewModel: MutableLiveData<PlaceOrderResponse> = MutableLiveData()


    fun getObserveData(): MutableLiveData<PlaceOrderResponse> {
        return placeOrderViewModel
    }

    fun placeOrderResponse(
        requireContext: Context,
        userData: PlaceOrder
    )
    {
        val retrofit = ServiceBuilder.getApiService(requireContext)
        retrofit.placeOrder(userData).enqueue(object : Callback<PlaceOrderResponse> {
                @SuppressLint("NullSafeMutableLiveData")
                override fun onFailure(call: Call<PlaceOrderResponse>, t: Throwable) {
                    placeOrderViewModel.postValue(null)
                }

                override fun onResponse(
                    call: Call<PlaceOrderResponse>,
                    response: Response<PlaceOrderResponse>
                ) {

                    if (response.isSuccessful) {
                        placeOrderViewModel.postValue(response.body())
                    } else {
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            Toast.makeText(
                                requireContext,
                                jObjError.getString("msg"),
                                Toast.LENGTH_LONG
                            ).show()
                        } catch (e: Exception) {
                            Toast.makeText(requireContext, e.message, Toast.LENGTH_LONG).show()
                        }


                    }
                }


            }
        )
    }




}


