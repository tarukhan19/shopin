package com.app.shopin.viewmodel.auth

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Util.Utils
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.app.shopin.model.MobileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MobileViewModel : ViewModel() {
    private var mobileData: MutableLiveData<MobileResponse?> = MutableLiveData()

    fun getObserve(): MutableLiveData<MobileResponse?> {
        return mobileData
    }

    fun makeMobileApiCall(context: Context, mobileNo: String) {
        val retIn = ServiceBuilder.getApiService(context)
        retIn.registerUserMobile(mobileNo).enqueue(object : Callback<MobileResponse> {
            override fun onResponse(call: Call<MobileResponse>, response: Response<MobileResponse>) {
                if (response.isSuccessful) {
                    mobileData.postValue(response.body())
                } else {
                    mobileData.postValue(null)

                }
            }

            override fun onFailure(call: Call<MobileResponse>, t: Throwable) {
                mobileData.postValue(null)

            }
        })
    }

}