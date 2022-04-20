package com.app.shopin.Orders.views.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shopin.Orders.Adapter.StoreItemHistoryAdapter
import com.app.shopin.Orders.models.*
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ActivityOrderHistoryDetailListingBinding
import com.app.shopin.databinding.ItemCurbsidePickupBinding
import com.app.shopin.databinding.ItemRatingReviewBinding
import com.app.shopin.databinding.ItemTipAmountBinding
import com.app.shopin.homePage.models.CartChildData
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.utils.OpenDialogBox
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_order_history_detail_listing.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import retrofit2.HttpException
import retrofit2.Response
import java.util.*
class OrderHistoryDetailListingActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityOrderHistoryDetailListingBinding
    lateinit var orderid: String
    lateinit var storeid: String
    lateinit var store_order_type: String
    lateinit var ordresponse:OrderHistoryDetailsResponse
    lateinit var ratingReviewResponse: Response<RatingReviewResponse>
    lateinit var tipResponse: Response<TipResponse>
    lateinit var curbsidemsgResponse: Response<CurbsideMsgResponse>
    lateinit var order_status: String
    lateinit var order_no:String
    lateinit var rating: String
    lateinit var review: String
    lateinit var tipamount: String
    lateinit var tipcomment: String
    lateinit var curbsidemsg:String
     lateinit var rrbindingSheet:ItemRatingReviewBinding
    lateinit var curbsidebindingSheet:ItemCurbsidePickupBinding
    lateinit var tipbindingSheet:ItemTipAmountBinding
    var ordertipJson: String = ""
    lateinit var orderrateJson: JSONObject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_order_history_detail_listing)
        initialize()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initialize() {

        orderid = intent.getStringExtra("orderid")!!
        toolbar.titleTV.text = getString(R.string.orderhsitory)
        toolbar.back_LL.setOnClickListener(this)
        ratingRB.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                try {
                    openRatingReviewDialog()
                } catch (e: java.lang.Exception)
                {Log.e("exc", e.localizedMessage!!.toString())}
            }
            return@OnTouchListener true
        })
        progressbarLL.visibility = View.VISIBLE
        button2LL.setOnClickListener(this)
        button1LL.setOnClickListener(this)
        issuewithitemTV.setOnClickListener(this)
        callApiFunction("orderdetail")
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back_LL -> {
                finish()
            }

            R.id.button1LL -> {

                if (store_order_type.equals("Curbside Pickup")) {
                    openCurbSideDialog()
                }

            }
            R.id.button2LL -> {

                if (store_order_type.equals("Delivery") && order_status.equals("Completed")) {
                    button2LL.visibility = VISIBLE
                   // if (ordertipJson.equals("{}")) {
                        openTipPopUp()
                   // }
                }

            }
            R.id.issuewithitemTV-> {
                val in7 = Intent(this, IssueWithItemsActivity::class.java)
                in7.putExtra("order_no",order_no)
                startActivity(in7)
            }
        }
    }




    @SuppressLint("NotifyDataSetChanged")
    private fun setData(data: OrderHistoryDetail) {
        Log.e("data",data.toString())

        storeid = data.store!!

        val orderratedata = data.store_rating!!
        val gson = Gson()
        val jsonString = gson.toJson(orderratedata)
        try {
            orderrateJson = JSONObject(jsonString)
        } catch (e: JSONException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }


        val tipdata = data.order_tip!!
        val tipgson = Gson()
        ordertipJson = tipgson.toJson(tipdata)
        order_no=data.order_no.toString()
       orderidTV.setText(order_no)

        CoroutineScope(Dispatchers.Main).launch {
            val date = Utils.dateFormat(data.created_date)
            dateTV.text = date

        }
        storenameTV.setText(data.store_name)
        Utils.setImage(shopIV, data.store_image, R.drawable.store)
        store_order_type = data.store_order_type
        order_status = data.order_status
        buttonTV1.text = store_order_type
        totalpriceTV.text = "Total \n$ " + data.store_total_amount
        if (!orderrateJson.equals("{}")) {
            val avgrating = orderrateJson.optDouble("rating__avg")
            val myrating = orderrateJson.optDouble("user_rating")
            val reviewcount = orderrateJson.optString("rating__count")

            totalreviewTV.setText("$reviewcount Reviews")

                if (avgrating.toString().equals("NaN"))
                {
                    ratingTV.setText("0.0")
                }
                else
                {
                    ratingTV.setText(String.format("%.2f", avgrating))
                }




            ratingRB.rating = myrating.toFloat()
            if (avgrating.toString().equals("0.0")) {
                ratingLL.setBackgroundResource(R.drawable.ratinggrayback)
            } else {
                ratingLL.setBackgroundResource(R.drawable.ratingyellowback)
            }
        }


        if (store_order_type.equals("Delivered")) {
            button1LL.setBackgroundResource(R.drawable.ord_deliv_capsule)
        } else if (store_order_type.equals("Pending")) {
            button1LL.setBackgroundResource(R.drawable.ord_pending_capsule)
        } else if (store_order_type.equals("Cancelled")) {
            button1LL.setBackgroundResource(R.drawable.ord_cancel_capsule)
        }

        try {
            val storeInventoryData: ArrayList<CartChildData> =
                data.order_item!!
            val adapter = StoreItemHistoryAdapter(this, storeInventoryData,order_no)
            val layoutManager = LinearLayoutManager(this)
            productRV.setHasFixedSize(false)
            productRV.layoutManager = layoutManager
            productRV.adapter = adapter
            adapter.notifyDataSetChanged()

        } catch (e: Exception) {
            Log.e("exce", e.message.toString())
        }

        if (order_status.equals("Rejected")) {
            buttonIV1.visibility = GONE
        }

        if (store_order_type.equals("Delivery") && order_status.equals("Completed")) {
            button2LL.visibility = VISIBLE
            if (ordertipJson.equals("{}")) {
                buttonTV2.setText("Tip")
            } else {
               buttonTV2.setText("Tipped")
                buttonIV2.visibility = GONE

            }
        }
    }


    fun callApiFunction(from:String)
    {
        val service = ServiceBuilder.getApiService(this)
        CoroutineScope(Dispatchers.IO).launch{
            try {
                if (from.equals("orderdetail"))
                {
                    Log.e("orderid",orderid)
                    ordresponse = service.getOrderDetails(orderid)
                }
                else if (from.equals("ratingreview"))
                {
                    ratingReviewResponse = service.ratingReviewSubmit(storeid, orderid, rating, review)
                }
                else if (from.equals("tip"))
                {
                    tipResponse = service.tipSubmit(storeid, orderid, tipcomment, tipamount)
                }
                else if (from.equals("curbsidemsg"))
                {
                    curbsidemsgResponse= service.curbsidemsgSubmit(orderid, curbsidemsg)
                }
            }catch (e:java.lang.Exception)
            {

            }

            withContext(Dispatchers.Main) {
                try {
                    progressbarLL.visibility = GONE
                    if (from.equals("orderdetail"))
                    {
                        if (ordresponse.status==true)
                        {
                            setData(ordresponse.data.order_drtail)

                        }
                    }
                    else if (from.equals("ratingreview"))
                    {


                        if (ratingReviewResponse.isSuccessful)
                        {
                            rrbindingSheet.ratereviewLL.visibility= GONE
                            rrbindingSheet.thanksLL.visibility= VISIBLE
                            ordresponse = service.getOrderDetails(orderid)
                            setData(ordresponse.data.order_drtail)
                        }
                        else
                        {
                            val gson = Gson()
                            val type = object : TypeToken<ErrorResponse>() {}.type
                            val errorResponse: ErrorResponse? = gson.fromJson(ratingReviewResponse.errorBody()!!.charStream(), type)
                            OpenDialogBox.openDialog(
                                this@OrderHistoryDetailListingActivity,
                                "Error!",
                                errorResponse!!.msg,
                                ""
                            )

                        }

                    }
                    else if (from.equals("curbsidemsg"))
                    {

                        if (curbsidemsgResponse.isSuccessful)
                        {
                            Utils.showToast("Message has been sent.", this@OrderHistoryDetailListingActivity)

                        }
                        else
                        {
                            val gson = Gson()
                            val type = object : TypeToken<ErrorResponse>() {}.type
                            val errorResponse: ErrorResponse? = gson.fromJson(curbsidemsgResponse.errorBody()!!.charStream(), type)
                            OpenDialogBox.openDialog(
                                this@OrderHistoryDetailListingActivity,
                                "Error!",
                                errorResponse!!.msg,
                                ""
                            )

                        }

                    }
                    else if (from.equals("tip"))
                    {

                        if (tipResponse.isSuccessful)
                        {
                            Utils.showToast("Thank you for your tip", this@OrderHistoryDetailListingActivity)
                            buttonTV2.setText("Tipped")
                            ordresponse = service.getOrderDetails(orderid)
                            setData(ordresponse.data.order_drtail)

                        }
                      else
                        {
                            val gson = Gson()
                            val type = object : TypeToken<ErrorResponse>() {}.type
                            var errorResponse: ErrorResponse? = gson.fromJson(tipResponse.errorBody()!!.charStream(), type)
                            OpenDialogBox.openDialog(
                                this@OrderHistoryDetailListingActivity,
                                "Error!",
                                errorResponse!!.msg,
                                ""
                            )

                        }

                    }


                } catch (e: HttpException) {
                    Utils.showToast(
                        "Exception ${e.message}",
                        this@OrderHistoryDetailListingActivity
                    )

                } catch (e: Throwable) {
                    Utils.showToast(
                        "t>> "+e.localizedMessage,
                        this@OrderHistoryDetailListingActivity
                    )

                }
            }

        }
    }


    fun openCurbSideDialog() {
        val dialog = BottomSheetDialog(this)
        curbsidebindingSheet = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_curbside_pickup,
            null,
            false
        )
        dialog.setContentView(curbsidebindingSheet.root)
        dialog.show()

        curbsidebindingSheet.curbbackbtn.setOnClickListener { dialog.dismiss() }
        curbsidebindingSheet.iamherebtn.setOnClickListener {
            curbsidemsg = curbsidebindingSheet.curbsidemsgET.text.toString()
            if (curbsidemsg.isEmpty()) {
                Utils.showToast("Enter msg here", this)
            } else
            {
                dialog.dismiss()
                callApiFunction("curbsidemsg")

            }

        }

    }

    private fun openRatingReviewDialog() {
        val bottomSheet = BottomSheetDialog(this)
         rrbindingSheet = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_rating_review,
            null,
            false
        )
        bottomSheet.setContentView(rrbindingSheet.root)
        bottomSheet.show()

            rrbindingSheet.rrbackbtn.setOnClickListener { bottomSheet.dismiss() }
            rrbindingSheet.rrsubmitBTN.setOnClickListener {
                rating = rrbindingSheet.ratingbar.rating.toString()
                review = rrbindingSheet.reviewET.text.toString()
                Log.e("rating",rating)
                progressbarLL.visibility = VISIBLE
                callApiFunction("ratingreview")

            }





    }

    private fun openTipPopUp() {
        val dialog = BottomSheetDialog(this)
        var isTipEnter = false
        tipbindingSheet = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.item_tip_amount,
            null,
            false
        )
        dialog.setContentView(tipbindingSheet.root)
        dialog.show()

        tipbindingSheet.tipbackbtn.setOnClickListener { dialog.dismiss() }
        tipbindingSheet.tipsubmitBTN.setOnClickListener {
            if (!isTipEnter) {
                tipamount =  tipbindingSheet.customET.text.toString()
            }
            tipcomment =  tipbindingSheet.tipexpET.text.toString()
            callApiFunction("tip")
            dialog.dismiss()

        }
        tipbindingSheet.amount2TV.setOnClickListener {
            tipbindingSheet.amount2TV.setBackgroundResource(R.drawable.orangecapsule)
            tipbindingSheet.amount2TV.setTextColor(resources.getColor(R.color.white))
            tipbindingSheet.amount4TV.setBackgroundResource(R.drawable.tabackgrnd)
            tipbindingSheet.amount4TV.setTextColor(resources.getColor(R.color.black))
            tipbindingSheet.amount5TV.setBackgroundResource(R.drawable.tabackgrnd)
            tipbindingSheet.amount5TV.setTextColor(resources.getColor(R.color.black))
            tipamount = "2"
            isTipEnter = true

        }
        tipbindingSheet.amount4TV.setOnClickListener {
            tipbindingSheet.amount4TV.setBackgroundResource(R.drawable.orangecapsule)
            tipbindingSheet.amount4TV.setTextColor(resources.getColor(R.color.white))
            tipbindingSheet.amount2TV.setBackgroundResource(R.drawable.tabackgrnd)
            tipbindingSheet.amount2TV.setTextColor(resources.getColor(R.color.black))
            tipbindingSheet.amount5TV.setBackgroundResource(R.drawable.tabackgrnd)
            tipbindingSheet.amount5TV.setTextColor(resources.getColor(R.color.black))
            tipamount = "4"
            isTipEnter = true

        }
        tipbindingSheet.amount5TV.setOnClickListener {
            tipbindingSheet.amount5TV.setBackgroundResource(R.drawable.orangecapsule)
            tipbindingSheet.amount5TV.setTextColor(resources.getColor(R.color.white))
            tipbindingSheet.amount2TV.setBackgroundResource(R.drawable.tabackgrnd)
            tipbindingSheet.amount2TV.setTextColor(resources.getColor(R.color.black))
            tipbindingSheet.amount4TV.setBackgroundResource(R.drawable.tabackgrnd)
            tipbindingSheet.amount4TV.setTextColor(resources.getColor(R.color.black))
            tipamount = "5"
            isTipEnter = true
        }

        tipbindingSheet.customET.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                tipbindingSheet.amount5TV.setBackgroundResource(R.drawable.tabackgrnd)
                tipbindingSheet.amount5TV.setTextColor(resources.getColor(R.color.black))
                tipbindingSheet.amount2TV.setBackgroundResource(R.drawable.tabackgrnd)
                tipbindingSheet.amount2TV.setTextColor(resources.getColor(R.color.black))
                tipbindingSheet.amount4TV.setBackgroundResource(R.drawable.tabackgrnd)
                tipbindingSheet.amount4TV.setTextColor(resources.getColor(R.color.black))
                tipamount = "0"
                isTipEnter = false

            }
        })


    }
}