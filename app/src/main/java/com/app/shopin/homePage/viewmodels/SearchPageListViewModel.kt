package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.homePage.Adapter.AllStoreDataAdapter
import com.app.shopin.homePage.Adapter.SearchCategoryAdapter
import com.app.shopin.homePage.models.*
import com.app.shopin.utils.OpenDialogBox
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPageListViewModel : ViewModel() {

    var categoryListLiveData: MutableLiveData<StoreCategoryListResponse> = MutableLiveData()
    var searchListLiveData: MutableLiveData<SearchListResponse> = MutableLiveData()

    private var searchCategoryAdapter: SearchCategoryAdapter = SearchCategoryAdapter()

    fun getSearchPageListObserver(): MutableLiveData<StoreCategoryListResponse> {
        return categoryListLiveData
    }

    fun getSearchDataListObserver(): MutableLiveData<SearchListResponse> {
        return searchListLiveData
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setCategoryAdapter(searchPageData: ArrayList<StoreCategoryData>) {
        searchCategoryAdapter.setListData(searchPageData)
        searchCategoryAdapter.notifyDataSetChanged()
    }

    fun getCategoryAdapter(): SearchCategoryAdapter {
        return searchCategoryAdapter
    }


    fun getCategoryList(requireContext: Context, key: Int) {
        val request = ServiceBuilder.getApiService(requireContext)
        request.getSearchPageData(key).enqueue(object : Callback<StoreCategoryListResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<StoreCategoryListResponse>,
                response: Response<StoreCategoryListResponse>
            ) {
                if (response.isSuccessful) {
                    categoryListLiveData.postValue(response.body())
                } else {
                    categoryListLiveData.postValue(null)
                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<StoreCategoryListResponse>, t: Throwable) {
                categoryListLiveData.postValue(null)
            }
        })
    }

    fun getSearchList(requireContext: Context, key: Int, searchstring: String) {
        val request = ServiceBuilder.getApiService(requireContext)
        request.getSearchListData(key, searchstring).enqueue(object : Callback<SearchListResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<SearchListResponse>,
                response: Response<SearchListResponse>
            ) {
                // Utils.showToast(response.isSuccessful.toString(),requireContext)
                if (response.isSuccessful) {
                    searchListLiveData.postValue(response.body())
                } else {

                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    var errorResponse: ErrorResponse? =
                        gson.fromJson(response.errorBody()!!.charStream(), type)
                    searchListLiveData.postValue(null)
                    Log.e("erooorr", errorResponse!!.msg)
                    OpenDialogBox.openDialog(
                        requireContext,
                        "Error!",
                        errorResponse!!.msg,
                        ""
                    )


                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<SearchListResponse>, t: Throwable) {
                searchListLiveData.postValue(null)
            }
        })
    }


}