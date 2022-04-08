package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.models.AddToCartResponse
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.utils.OpenDialogBox
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddToCartViewModel : ViewModel() {
    var addtocartviewmodel: MutableLiveData<AddToCartResponse> = MutableLiveData()


    fun getObserveData(): MutableLiveData<AddToCartResponse> {
        return addtocartviewmodel
    }

    fun addToCartResponse(requireContext: Context,ordertype:String, price: String, quantity: String, is_update: String
                       , inventory: String, tax_amount: String,total_amount: String,
                          tax_percentage : String, store : String) {

        val request = ServiceBuilder.getApiService(requireContext)
        request.addToCart(ordertype,price,quantity,is_update,inventory, tax_amount, total_amount, tax_percentage,store).enqueue(object :
            Callback<AddToCartResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<AddToCartResponse>,
                response: Response<AddToCartResponse>
            ) {
                if (response.isSuccessful) {
                    Utils.showToast("Item added successfully",requireContext)
                    addtocartviewmodel.postValue(response.body())
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    var errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    addtocartviewmodel.postValue(null)
                    OpenDialogBox.openDialog(
                        requireContext,
                        "Error!",
                        errorResponse!!.msg,
                        ""
                    )

                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<AddToCartResponse>, t: Throwable) {
                addtocartviewmodel.postValue(null)
            }

        })

    }
}


