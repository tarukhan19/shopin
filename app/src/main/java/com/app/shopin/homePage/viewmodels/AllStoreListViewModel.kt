package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.homePage.Adapter.AllStoreDataAdapter
import com.app.shopin.homePage.Adapter.AllStoreListAdapter
import com.app.shopin.homePage.models.AllStoreCategoryData
import com.app.shopin.homePage.models.AllStoreDataValues
import com.app.shopin.homePage.models.AllStoreListResponse
import com.customer.gogetme.Retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllStoreListViewModel : ViewModel() {
    private var allStoreListLiveData: MutableLiveData<AllStoreListResponse> = MutableLiveData()
    private var allStoreListAdapter: AllStoreListAdapter = AllStoreListAdapter()
   lateinit var  allStoreDataAdapter: AllStoreDataAdapter
    fun getAllStoreListObserver(): MutableLiveData<AllStoreListResponse> {
        return allStoreListLiveData
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setAllStoreAdapter(
        allStoreCategoryData: ArrayList<AllStoreCategoryData>,
        context: FragmentActivity
    ) {
        allStoreListAdapter.setListData(allStoreCategoryData,context)
        allStoreListAdapter.notifyDataSetChanged()
    }

    fun getAllStoreAdapter(): AllStoreListAdapter {
        return allStoreListAdapter
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setallStoreDataAdapter(allStoreDataValues: ArrayList<AllStoreDataValues>) {
//        allStoreDataAdapter.setListData(allStoreDataValues, false)
        allStoreDataAdapter.notifyDataSetChanged()
    }

    fun getallStoreDataAdapter(): AllStoreDataAdapter {
        return allStoreDataAdapter
    }
    fun getAllStoryList(requireContext: Context) {

        val request = ServiceBuilder.getApiService(requireContext)
        request.getAllStoreListData().enqueue(object : Callback<AllStoreListResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<AllStoreListResponse>,
                response: Response<AllStoreListResponse>
            ) {
                if (response.isSuccessful) {
                    allStoreListLiveData.postValue(response.body())
                } else {
                    allStoreListLiveData.postValue(null)
                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<AllStoreListResponse>, t: Throwable) {
                allStoreListLiveData.postValue(null)
            }

        })

    }
}
