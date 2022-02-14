package com.app.shopin.UserAuth.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.UserAuth.model.LoadProfileResponse
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.utils.OpenDialogBox
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoadProfileViewModel : ViewModel() {
    private var loadprofile: MutableLiveData<LoadProfileResponse?> = MutableLiveData()

    fun getObserve(): MutableLiveData<LoadProfileResponse?> {
        return loadprofile
    }

    fun makeLoadProfileApiCall(context: Context) {
        val retIn = ServiceBuilder.getApiService(context)
        retIn.loadProfile().enqueue(object : Callback<LoadProfileResponse> {
            override fun onResponse(call: Call<LoadProfileResponse>, response: Response<LoadProfileResponse>) {
                if (response.isSuccessful) {
                    loadprofile.postValue(response.body())
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    val errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    Utils.showToast(errorResponse!!.msg,context)
                    loadprofile.postValue(null)
                }
            }

            override fun onFailure(call: Call<LoadProfileResponse>, t: Throwable) {
                loadprofile.postValue(null)

            }
        })
    }

}