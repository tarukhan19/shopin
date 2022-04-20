package com.app.shopin.Orders.views.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shopin.Orders.Adapter.IssueWithItemListAdapter
import com.app.shopin.Orders.models.IssueWithItemData
import com.app.shopin.Orders.models.SelectedIssue
import com.app.shopin.Orders.models.SendSelectedIssue
import com.app.shopin.Orders.viewmodels.IssueWithItemViewModel
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ActivityIssueWithItemsBinding
import com.app.shopin.homePage.views.Activity.CartPageActivity
import com.app.shopin.utils.OpenDialogBox
import com.app.shopin.utils.TimeDateConversion
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_issue_with_items.*
import kotlinx.android.synthetic.main.activity_issue_with_items.ratingLL
import kotlinx.android.synthetic.main.activity_issue_with_items.ratingTV
import kotlinx.android.synthetic.main.activity_issue_with_items.storenameTV
import kotlinx.android.synthetic.main.activity_issue_with_items.toolbar
import kotlinx.android.synthetic.main.activity_issue_with_items.totalreviewTV
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException

class IssueWithItemsActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var issuewithitemVM: IssueWithItemViewModel
    var order_no = ""
    lateinit var binding: ActivityIssueWithItemsBinding
     var  issueList=ArrayList<SelectedIssue>()

    companion object
    {
        @SuppressLint("StaticFieldLeak")
        var issuewithItemsActivity: IssueWithItemsActivity? = null

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_issue_with_items)
        initialize()
    }

    fun initialize() {
        issuewithItemsActivity=this
        order_no = intent.getStringExtra("order_no")!!
        Log.e("order_no", order_no)
        issuewithitemVM = ViewModelProvider(this).get(IssueWithItemViewModel::class.java)
        toolbar.titleTV.text = getString(R.string.issuewithitem)
        toolbar.back_LL.setOnClickListener(this)
        nextBTN.setOnClickListener(this)
        loadIssueWithItem()
    }
    fun getInstance(): IssueWithItemsActivity? {
        return issuewithItemsActivity
    }
    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.back_LL -> {
                finish()
            }
            R.id.nextBTN -> {

                progressbarLL.visibility=View.VISIBLE
                submitIssueWithItem()


            }
        }
    }

    fun loadIssueWithItem() {
        issuewithitemVM.getissueWithItemObserver().removeObservers(this)
        issuewithitemVM.getissueWithItemObserver()
            .observe(this) {
                if (it != null) {

                    val data = it.data
                    setData(data)

                }
            }
        issuewithitemVM.getissueWithItemDetail(this, order_no)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: IssueWithItemData)
    {
        val orderDetail = data.order_detail
        storenameTV.text = orderDetail.store_name
        addressTV.text = orderDetail.address
        storereturnpolicyTV.text = orderDetail.return_policy
        val storerating = orderDetail.store_rating
        val gson = Gson()
        val jsonString = gson.toJson(storerating)
        var orderrateJson = JSONObject()

        try {
            orderrateJson = JSONObject(jsonString)
            if (!orderrateJson.equals("{}")) {
                val avgrating = orderrateJson.optDouble("rating__avg")
                val reviewcount = orderrateJson.optString("rating__count")

                totalreviewTV.setText("$reviewcount Reviews")

                if (avgrating.toString().equals("NaN")) {
                    ratingTV.setText("0.0")
                } else {
                    ratingTV.setText(String.format("%.2f", avgrating))
                }

                if (avgrating.toString().equals("0.0")) {
                    ratingLL.setBackgroundResource(R.drawable.ratinggrayback)
                } else {
                    ratingLL.setBackgroundResource(R.drawable.ratingyellowback)
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }


        try {
        val storeTime = data.store_timmings
        val openTime = TimeDateConversion.convertTime(storeTime.opening_time)
        val closetime = TimeDateConversion.convertTime(storeTime.closing_time)
        binding.storetimeTV.text = openTime + " - " + closetime
        } catch (e: Exception) {
            Log.e("exce", e.message.toString())
        }


        try {
            val issues_keys=data.order_detail.issues_keys
            val storeInventoryData = data.order_detail.order_item!!
            val adapter = IssueWithItemListAdapter(this, storeInventoryData, issues_keys!!)
            val layoutManager = LinearLayoutManager(this)
            productRV.setHasFixedSize(false)
            productRV.setNestedScrollingEnabled(false);
            productRV.layoutManager = layoutManager
            productRV.adapter = adapter
            adapter.notifyDataSetChanged()

        } catch (e: Exception) {
            Log.e("exce", e.message.toString())
        }

    }


    fun runThread(selectedArrayList: ArrayList<SelectedIssue>,from:String)
    {
        object : Thread()
        {
            @SuppressLint("NotifyDataSetChanged")
            override fun run() {
                try
                {
                    if (from.equals("api"))
                    {
                        issueList=selectedArrayList

                    }
                    else
                    {

                    }

                }
                catch (e: InterruptedException)
                {
                    e.printStackTrace()
                }
            }
        }.start()
    }


    fun submitIssueWithItem()
    {

        val sendSelectedIssue=SendSelectedIssue(
            issueList, order_no
        )
        Log.e("sendSelectedIssue",sendSelectedIssue.toString())
        val request = ServiceBuilder.getApiService(this)


        CoroutineScope(Dispatchers.IO).launch {
            val response = request.createIssueWithItem(sendSelectedIssue)
            withContext(Dispatchers.Main) {
                try {
                    progressbarLL.visibility=View.GONE

                    if (response.isSuccessful) {
                        loadIssueWithItem()
                        OpenDialogBox.openDialog(this@IssueWithItemsActivity,"Success","Your Query has been submitted!","issue")

                    } else {
                    }
                } catch (e: HttpException) {
                    progressbarLL.visibility=View.GONE

                    Utils.showToast("Exception ${e.message}", this@IssueWithItemsActivity)

                } catch (e: Throwable) {
                    progressbarLL.visibility=View.GONE

                    Utils.showToast("Something else went wrong", this@IssueWithItemsActivity)

                }
            }
        }

    }
}