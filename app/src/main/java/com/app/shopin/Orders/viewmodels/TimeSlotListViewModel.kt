package com.app.shopin.Orders.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Orders.models.TimeSlotListResponse
import com.customer.gogetme.Retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TimeSlotListViewModel : ViewModel() {
    private var timeSlotListResponse: MutableLiveData<TimeSlotListResponse> = MutableLiveData()

    fun getTimeSlotListObserver(): MutableLiveData<TimeSlotListResponse> {
        return timeSlotListResponse
    }

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
                    timeSlotListResponse.postValue(null)
                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<TimeSlotListResponse>, t: Throwable) {
                timeSlotListResponse.postValue(null)
            }

        })

    }
}
