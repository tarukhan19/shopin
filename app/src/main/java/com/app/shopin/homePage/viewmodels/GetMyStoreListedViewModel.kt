package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.homePage.models.GetMyStoreListedResponse
import com.app.shopin.utils.OpenDialogBox
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GetMyStoreListedViewModel : ViewModel() {
    var getMyStoreListedResponseLiveData: MutableLiveData<GetMyStoreListedResponse> = MutableLiveData()


    fun getObserveData(): MutableLiveData<GetMyStoreListedResponse> {
        return getMyStoreListedResponseLiveData
    }

    fun getMyStoreResp(requireContext: Context, name: String, businessname: String, businessaddress: String
                                  , categoryId: String, emailid: String,
                                  mobileno: String, status : String) {

        val request = ServiceBuilder.getApiService(requireContext)
        request.getMyStoreListed(name,businessname,businessaddress,categoryId, emailid, mobileno, status).enqueue(object :
            Callback<GetMyStoreListedResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<GetMyStoreListedResponse>,
                response: Response<GetMyStoreListedResponse>
            ) {
                if (response.isSuccessful) {
                    getMyStoreListedResponseLiveData.postValue(response.body())
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    var errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    getMyStoreListedResponseLiveData.postValue(null)
                    OpenDialogBox.openDialog(requireContext,"Error!", errorResponse!!.msg)

                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<GetMyStoreListedResponse>, t: Throwable) {
                getMyStoreListedResponseLiveData.postValue(null)
            }

        })

    }
}


