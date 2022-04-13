package com.app.shopin.Orders.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.*

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.app.shopin.Orders.models.TimeSlotListResponse
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.utils.OpenDialogBox
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimeSlotListViewModel : ViewModel() {
    private var timeSlotListResponse: MutableLiveData<TimeSlotListResponse> = MutableLiveData()

    fun getTimeSlotListObserver(): MutableLiveData<TimeSlotListResponse> {
        return timeSlotListResponse
    }

//    fun gettimeSlotList(requireContext: Context, id:String) {
//
//        val request = ServiceBuilder.getApiService(requireContext)
//
//        viewModelScope.launch {
//            request.getTimeslotList(id).catch { e->
//                    Log.d("main", "getPost: ${e.message}")
//                }
//                .collect {postData1->
//                    postData.value=postData1
//                }
//        }
//    }

    fun getTimeSlotList(requireContext: Context, id:String) {
        val request = ServiceBuilder.getApiService(requireContext)
        request.getTimeslotList(id).enqueue(object : Callback<TimeSlotListResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<TimeSlotListResponse>,
                response: Response<TimeSlotListResponse>
            ) {
                if (response.isSuccessful) {
                    timeSlotListResponse.postValue(response.body())
                } else {

                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    var errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    timeSlotListResponse.postValue(null)
                    OpenDialogBox.openDialog(
                        requireContext,
                        "Error!",
                        errorResponse!!.msg,
                        ""
                    )

                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<TimeSlotListResponse>, t: Throwable) {
                timeSlotListResponse.postValue(null)
            }

        })

    }
}
