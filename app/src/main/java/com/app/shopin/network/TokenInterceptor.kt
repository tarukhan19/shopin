package com.customer.gogetme.Retrofit

import android.content.Context
import android.util.Log
import com.app.shopin.utils.Constant
import com.app.shopin.utils.MyApplication
import com.app.shopin.utils.Preference
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

 class TokenInterceptor(context: Context) : Interceptor{
    val context = MyApplication.appContext

    override fun intercept(chain: Interceptor.Chain): Response
    {
        lateinit var newRequest:Request
        val token:String= Preference.getInstance(context)?.getString(Constant.KEY_TOKEN).toString()
        Log.e("token",token)
        if (!token.isEmpty())
        {
             newRequest = chain.request().newBuilder()
                .header("Authorization", "Token $token")
                .build()
        }
        else
        {
            newRequest = chain.request().newBuilder()
                .header("Authorization", "")
                .build()
        }
        return chain.proceed(newRequest)
    }
}