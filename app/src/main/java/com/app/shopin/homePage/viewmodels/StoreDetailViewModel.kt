package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.homePage.models.StoreDetailApiResponse
import com.customer.gogetme.Retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreDetailViewModel : ViewModel()
{
    var storeDetailLiveData: MutableLiveData<StoreDetailApiResponse> = MutableLiveData()

    fun getStoreDetailObserver(): MutableLiveData<StoreDetailApiResponse> {
        return storeDetailLiveData
    }

    fun getStoreDetail(requireContext: Context, key: String)
    {
        val request = ServiceBuilder.getApiService(requireContext)
        request.fetchStoreDetail(key).enqueue(object : Callback<StoreDetailApiResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<StoreDetailApiResponse>,
                response: Response<StoreDetailApiResponse>
            )
            {
                if (response.isSuccessful)
                {
                    storeDetailLiveData.postValue(response.body())
                }
                else
                {
                    storeDetailLiveData.postValue(null)
                }
            }
            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<StoreDetailApiResponse>, t: Throwable) {
                storeDetailLiveData.postValue(null)
            }
        })
    }
}
