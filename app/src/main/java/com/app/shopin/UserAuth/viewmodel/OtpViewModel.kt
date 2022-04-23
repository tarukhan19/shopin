package com.app.shopin.viewmodel.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.models.ErrorResponse
import com.bumptech.glide.util.Util
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.app.shopin.model.OtpResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OtpViewModel:ViewModel() {
    private  var otpData: MutableLiveData<OtpResponse?> = MutableLiveData()

    fun getObserveData(): MutableLiveData<OtpResponse?>{
        return otpData
    }

    fun makeOtpApiCall(context: Context,email:String,otp:String){

        val retrofitInstance = ServiceBuilder.getApiService(context)
        retrofitInstance.verifyUser("1","oo",email,otp).enqueue(object : Callback<OtpResponse> {
            override fun onResponse(call: Call<OtpResponse>, response: Response<OtpResponse>) {
                if(response.isSuccessful){
                   otpData.postValue(response.body())
                }else{

                    otpData.postValue(null)
                }
            }

            override fun onFailure(call: Call<OtpResponse>, t: Throwable) {
                otpData.postValue(null)
            }
        })
    }
}