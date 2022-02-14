package com.app.shopin.homePage.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.Adapter.CategoryAdapter
import com.app.shopin.homePage.Adapter.SearchCategoryAdapter
import com.app.shopin.homePage.Adapter.StoreCategoryAdapter
import com.app.shopin.homePage.models.StoreCategoryData
import com.app.shopin.homePage.models.StoreCategoryListResponse
import com.customer.gogetme.Retrofit.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryListViewModel : ViewModel() {
    var storeCategoryListLiveData: MutableLiveData<StoreCategoryListResponse> = MutableLiveData()
    private var searchCategoryAdapter: SearchCategoryAdapter = SearchCategoryAdapter()
    private var categoryAdapter: CategoryAdapter = CategoryAdapter()
    private var storeCategoryAdapter: StoreCategoryAdapter = StoreCategoryAdapter()


    fun getCategoryListObserver(): MutableLiveData<StoreCategoryListResponse> {
        return storeCategoryListLiveData
    }

//    @SuppressLint("NotifyDataSetChanged")
//    fun setSearchAdapter(searchCategoryData: ArrayList<StoreCategoryData>) {
//        searchCategoryAdapter.setListData(searchCategoryData)
//        searchCategoryAdapter.notifyDataSetChanged()
//    }
//
//    fun getSearchAdapter(): SearchCategoryAdapter {
//        return searchCategoryAdapter
//    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMyStoreAdapter(storeCategoryData: ArrayList<StoreCategoryData>, context: Context) {
        storeCategoryAdapter.setListData(storeCategoryData,context)
        storeCategoryAdapter.notifyDataSetChanged()
    }

    fun getMyStoreAdapter(): StoreCategoryAdapter {
        return storeCategoryAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setStoreAdapter(storeCategoryData: ArrayList<StoreCategoryData>) {
        categoryAdapter.setListData(storeCategoryData)
        categoryAdapter.notifyDataSetChanged()
    }

    fun getStoreAdapter(): CategoryAdapter {
        return categoryAdapter
    }
    fun getCategoryList(requireContext: Context) {

        val request = ServiceBuilder.getApiService(requireContext)
        request.getCategoryData().enqueue(object : Callback<StoreCategoryListResponse> {
            @SuppressLint("NullSafeMutableLiveData")
            override fun onResponse(
                call: Call<StoreCategoryListResponse>,
                response: Response<StoreCategoryListResponse>
            ) {

                if (response.isSuccessful) {
                    storeCategoryListLiveData.postValue(response.body())
                } else {
                    storeCategoryListLiveData.postValue(null)
                }
            }

            @SuppressLint("NullSafeMutableLiveData")
            override fun onFailure(call: Call<StoreCategoryListResponse>, t: Throwable) {
                storeCategoryListLiveData.postValue(null)
            }

        })

    }
}
