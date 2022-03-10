package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.homePage.models.ProductDetailResponse
import com.customer.gogetme.Retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductDetailViewModel : ViewModel() {
    private var productDetailResponseLiveData: MutableLiveData<ProductDetailResponse> = MutableLiveData()


    fun getproductDetailObserveData(): MutableLiveData<ProductDetailResponse> {
        return productDetailResponseLiveData
    }

    fun getProdDetailResp(requireContext: Context, id: String) {

        val request = ServiceBuilder.getApiService(requireContext)
        request.prodDetail(id).enqueue(object :
            Callback<ProductDetailResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<ProductDetailResponse>,
                response: Response<ProductDetailResponse>
            ) {
                if (response.isSuccessful) {
                    productDetailResponseLiveData.postValue(response.body())
                } else {
                    productDetailResponseLiveData.postValue(null)
                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<ProductDetailResponse>, t: Throwable) {
                productDetailResponseLiveData.postValue(null)
            }

        })

    }
}
