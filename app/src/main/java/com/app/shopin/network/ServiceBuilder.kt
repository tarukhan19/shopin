package com.customer.gogetme.Retrofit

import android.content.Context
import com.app.shopin.network.ApiBaseUrl
import com.app.shopin.network.ApiServices
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

//
//
    private fun client(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(TokenInterceptor(context))
            .build()
    }
//
//
//    private val retrofit:Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl(ApiBaseUrl.BASE_URL)
//            .build()
//    }
//

    private lateinit var apiService: ApiServices


    fun getApiService(context: Context): ApiServices {
        if (!::apiService.isInitialized) {
            val retrofit = Retrofit.Builder()
                .baseUrl(ApiBaseUrl.BASE_URL) // change this IP for testing by your actual machine IP
                .addConverterFactory(GsonConverterFactory.create())
                .client(client(context))
                .build()

            apiService = retrofit.create(ApiServices::class.java)
        }
        return apiService
    }



}