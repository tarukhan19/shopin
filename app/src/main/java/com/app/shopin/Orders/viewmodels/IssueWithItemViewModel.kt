package com.app.shopin.Orders.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.shopin.Orders.models.IssueWithItemResponse
import com.app.shopin.Util.Utils
import com.customer.gogetme.Retrofit.ServiceBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class IssueWithItemViewModel : ViewModel() {
    private var issueWithItemViewModel: MutableLiveData<IssueWithItemResponse> = MutableLiveData()

    fun getissueWithItemObserver(): MutableLiveData<IssueWithItemResponse> {
        return issueWithItemViewModel
    }

    fun getissueWithItemDetail(requireContext: Context,orderno:String) {

        val request = ServiceBuilder.getApiService(requireContext)


        CoroutineScope(Dispatchers.IO).launch {
            val response = request.issuewithitemdetail(orderno)
            withContext(Dispatchers.Main) {
                try {

                    if (response.isSuccessful) {
                        issueWithItemViewModel.postValue(response.body())
                    } else {
                        Utils.showToast("error", requireContext)
                    }
                } catch (e: HttpException) {
                    Utils.showToast("Exception ${e.message}", requireContext)

                } catch (e: Throwable) {
                    Utils.showToast("Something else went wrong", requireContext)

                }
            }
        }

    }
}
