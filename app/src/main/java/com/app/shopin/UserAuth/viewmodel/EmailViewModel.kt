package com.app.shopin.viewmodel.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.models.ErrorResponse
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.app.shopin.model.EmailResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EmailViewModel : ViewModel() {
    private var emailData: MutableLiveData<EmailResponse?> = MutableLiveData()

    fun getObserve(): MutableLiveData<EmailResponse?> {
        return emailData
    }

    fun makeEmailApiCall(context: Context, email: String) {
        val retIn = ServiceBuilder.getApiService(context)
        retIn.registerUserEmail(email).enqueue(object : Callback<EmailResponse> {
            override fun onResponse(call: Call<EmailResponse>, response: Response<EmailResponse>) {
                if (response.isSuccessful) {
                    emailData.postValue(response.body())
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    val errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    Utils.showToast(errorResponse!!.msg,context)
                    emailData.postValue(null)

                }
            }

            override fun onFailure(call: Call<EmailResponse>, t: Throwable) {
                emailData.postValue(null)

            }
        })
    }

}