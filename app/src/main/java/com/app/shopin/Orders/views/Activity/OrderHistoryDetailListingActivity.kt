package com.app.shopin.Orders.views.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shopin.Orders.Adapter.StoreItemHistoryAdapter
import com.app.shopin.Orders.models.Ordertipdata
import com.app.shopin.Orders.viewmodels.OrderHistoryViewModel
import com.app.shopin.Orders.viewmodels.RatingReviewViewModel
import com.app.shopin.Orders.viewmodels.TipViewModel
import com.app.shopin.R
import com.app.shopin.UserAuth.view.EmailRegisterActivity
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ActivityOrderHistoryDetailListingBinding
import com.app.shopin.homePage.models.CartChildData
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_order_history_detail_listing.*
import kotlinx.android.synthetic.main.activity_order_history_list.toolbar
import kotlinx.android.synthetic.main.toolbar.view.*
import org.json.JSONObject
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class OrderHistoryDetailListingActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityOrderHistoryDetailListingBinding
    lateinit var orderHistoryViewModel: OrderHistoryViewModel
    lateinit var orderid: String
    lateinit var storeid: String
    lateinit var store_order_type: String
    lateinit var ratingReviewViewModel: RatingReviewViewModel
    lateinit var tipViewModel: TipViewModel
    lateinit var order_status:String
     var  ordertiplength: Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =
            DataBindingUtil.setContentView(this, R.layout.activity_order_history_detail_listing)
        initialize()
    }

    @SuppressLint("ClickableViewAccessibility")
    fun initialize() {
        orderid = intent.getStringExtra("orderid")!!
        orderHistoryViewModel = ViewModelProvider(this).get(OrderHistoryViewModel::class.java)
        ratingReviewViewModel= ViewModelProvider(this).get(RatingReviewViewModel::class.java)
        tipViewModel= ViewModelProvider(this).get(TipViewModel::class.java)
        toolbar.titleTV.text = getString(R.string.orderhsitory)
        toolbar.back_LL.setOnClickListener(this)
        ratingRB.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                openRatingReviewDialog()
            }
            return@OnTouchListener true
        })
        binding.progressbarLL.visibility = View.VISIBLE
        binding.button2LL.setOnClickListener(this)
        binding.button1LL.setOnClickListener(this)

        loadOrderHistoryDetails()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back_LL -> {
                finish()
            }

            R.id.button1LL -> {

                if (store_order_type.equals("Curbside Pickup"))
                {


                    openCurbSideDialog()


                }

            }
            R.id.button2LL -> {
                if (store_order_type.equals("Delivery") && order_status.equals("Completed"))
                {

//                    if (ordertiplength==0)
//                    {
                        openTipPopUp()
//                    }

                }

                if (store_order_type.equals("Curbside pickup"))
                {


                    openCurbSideDialog()


                }

            }
        }
    }



    @SuppressLint("NotifyDataSetChanged")
    fun loadOrderHistoryDetails() {
        orderHistoryViewModel.getAllStoreListObserver().removeObservers(this)
        orderHistoryViewModel.getAllStoreListObserver()
            .observe(this) {
                if (it != null) {
                    if (it.status_code == 200)
                    {
                        binding.cardview.visibility = View.VISIBLE
                        binding.progressbarLL.visibility = View.GONE
                        val data = it.data.order_drtail
                        storeid= data.store!!
                        val storedata = it.data.order_drtail
                        val storeratedata = it.data.order_drtail.store_rating
                        val tipdata = it.data.order_drtail.order_tip!!
                        val gson = Gson()
                        val json = gson.toJson(tipdata)
                        ordertiplength=json.length
                        Log.e("ordertipdata",ordertiplength.toString())

                        binding.orderidTV.setText(data.order_no)
                        dateFormat(data.created_date, binding.dateTV)
                        binding.storenameTV.setText(storedata.store_name)
                        Utils.setImage(binding.shopIV, storedata.store_image, R.drawable.store)
                        store_order_type = storedata.store_order_type
                        order_status=storedata.order_status
                        binding.buttonTV1.text = store_order_type
                        binding.totalpriceTV.text = "$ " + storedata.store_total_amount
                        binding.ratingTV.setText(storeratedata?.rating__avg)

                        if (storeratedata?.rating__avg.equals("0.0"))
                        {
                            binding.ratingLL.setBackgroundResource(R.drawable.ratinggrayback)
                        }
                        else
                        {
                            binding.ratingLL.setBackgroundResource(R.drawable.ratingyellowback)
                        }


                        binding.totalreviewTV.setText(storeratedata?.rating__count + " Ratings")
                        if (store_order_type.equals("Delivered")) {
                            binding.button1LL.setBackgroundResource(R.drawable.ord_deliv_capsule)
                        } else if (store_order_type.equals("Pending")) {
                            binding.button1LL.setBackgroundResource(R.drawable.ord_pending_capsule)
                        } else if (store_order_type.equals("Cancelled")) {
                            binding.button1LL.setBackgroundResource(R.drawable.ord_cancel_capsule)
                        }

                        try {
                            val storeInventoryData: ArrayList<CartChildData> =
                                storedata.order_item!!
                            val adapter = StoreItemHistoryAdapter(this, storeInventoryData)
                            val layoutManager = LinearLayoutManager(this)
                            binding.productRV.setHasFixedSize(false)
                            binding.productRV.layoutManager = layoutManager
                            binding.productRV.adapter = adapter
                            adapter.notifyDataSetChanged()

                        } catch (e: Exception) {
                            Log.e("exce", e.message.toString())
                        }
                        setButton()


                    } else {
                        binding.progressbarLL.visibility = View.GONE

                    }


                }
            }

        orderHistoryViewModel.getAllOrderHistoryList(this, orderid)
    }

    private fun setButton() {
        if (order_status.equals("Rejected"))
        {
            binding.buttonIV1.visibility=View.GONE
        }

        if (store_order_type.equals("Delivery") && order_status.equals("Completed"))
        {
            binding.button2LL.visibility=View.VISIBLE
        //    Log.e("ordertipdata",ordertipdata.has("id").toString())
            binding.buttonTV2.setText("Tip")

//            if (ordertiplength==0)
//            {
//                binding.buttonTV2.setText("Tip")
//            }
//            else
//            {
//                binding.buttonTV2.setText("Tipped")
//                binding.buttonIV2.visibility=View.GONE
//
//            }
        }
    }



    fun dateFormat(createdDate: String?, orderdateTV: TextView) {
        try {
            var date = createdDate
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val output = SimpleDateFormat("EEE, d MMM, yyyy")

            var d: Date? = null
            input.setTimeZone(TimeZone.getTimeZone("UTC"));
            d = input.parse(date)
            val formatted: String = output.format(d)
            Log.i("DATE", "" + formatted)
            orderdateTV.text = formatted

        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }


    private fun openRatingReviewDialog() {

        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.item_rating_review, null)
        val backbtn = view.findViewById<TextView>(R.id.backbtn)
        val ratingBar = view.findViewById<RatingBar>(R.id.ratingbar)
        val reviewET = view.findViewById<EditText>(R.id.reviewET)
        val submitBTN = view.findViewById<Button>(R.id.submitBTN)
        val ratereviewLL = view.findViewById<LinearLayout>(R.id.ratereviewLL)
        val thanksLL = view.findViewById<LinearLayout>(R.id.thanksLL)

        backbtn.setOnClickListener { dialog.dismiss() }
        submitBTN.setOnClickListener {
            val rating=ratingBar.rating.toString()
            val review=reviewET.text.toString()
            Log.e("rating",rating)



            binding.progressbarLL.visibility=View.VISIBLE
            reviewRatingSubmit(storeid ,rating,review,ratereviewLL,thanksLL)


        }

        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()


    }

    private fun reviewRatingSubmit(
        id: String,
        rating: String,
        review: String,
        ratereviewLL: LinearLayout,
        thanksLL: LinearLayout
    )
    {
        progressbarLL.visibility = View.VISIBLE
        ratingReviewViewModel.getRatingReviewObserver().removeObservers(this)
        ratingReviewViewModel.getRatingReviewObserver().observe(this) {

            if (it?.status == true) {
                progressbarLL.visibility = View.GONE
                ratereviewLL.visibility=View.GONE
                thanksLL.visibility=View.VISIBLE
            } else {
                progressbarLL.visibility = View.GONE
            }
        }
        ratingReviewViewModel.ratingReviewSubmission(this,id,rating,review)
    }


    private fun openTipPopUp()
    {
        val dialog = BottomSheetDialog(this)
        var tipamount:Int=0
        var isTipEnter=false

        val view = layoutInflater.inflate(R.layout.item_tip_amount, null)
        val backbtn = view.findViewById<TextView>(R.id.backbtn)
        val submitBTN = view.findViewById<Button>(R.id.submitBTN)
        val  amount2TV= view.findViewById<TextView>(R.id.amount2TV)
        val  amount4TV= view.findViewById<TextView>(R.id.amount4TV)
        val  amount5TV= view.findViewById<TextView>(R.id.amount5TV)
        val  customET= view.findViewById<TextView>(R.id.customET)
        val  reviewET= view.findViewById<TextView>(R.id.reviewET)

        backbtn.setOnClickListener { dialog.dismiss() }
        submitBTN.setOnClickListener {
            if (!isTipEnter)
            {
                val tipamounts=customET.text.toString()
                tipamount=tipamounts.toInt()
            }
            val review=reviewET.text.toString()
            Log.e("tipamount",tipamount.toString())
            submitTip(tipamount,review)
            dialog.dismiss()

        }
        amount2TV.setOnClickListener {
            amount2TV.setBackgroundResource(R.drawable.orangecapsule)
            amount2TV.setTextColor(resources.getColor(R.color.white))
            amount4TV.setBackgroundResource(R.drawable.tabackgrnd)
            amount4TV.setTextColor(resources.getColor(R.color.black))
            amount5TV.setBackgroundResource(R.drawable.tabackgrnd)
            amount5TV.setTextColor(resources.getColor(R.color.black))
            tipamount=2
            isTipEnter=true

        }
        amount4TV.setOnClickListener {
            amount4TV.setBackgroundResource(R.drawable.orangecapsule)
            amount4TV.setTextColor(resources.getColor(R.color.white))
            amount2TV.setBackgroundResource(R.drawable.tabackgrnd)
            amount2TV.setTextColor(resources.getColor(R.color.black))
            amount5TV.setBackgroundResource(R.drawable.tabackgrnd)
            amount5TV.setTextColor(resources.getColor(R.color.black))
            tipamount=4
            isTipEnter=true

        }
        amount5TV.setOnClickListener {
            amount5TV.setBackgroundResource(R.drawable.orangecapsule)
            amount5TV.setTextColor(resources.getColor(R.color.white))
            amount2TV.setBackgroundResource(R.drawable.tabackgrnd)
            amount2TV.setTextColor(resources.getColor(R.color.black))
            amount4TV.setBackgroundResource(R.drawable.tabackgrnd)
            amount4TV.setTextColor(resources.getColor(R.color.black))
            tipamount=5
            isTipEnter=true
        }

        customET.setOnFocusChangeListener(OnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                amount5TV.setBackgroundResource(R.drawable.tabackgrnd)
                amount5TV.setTextColor(resources.getColor(R.color.black))
                amount2TV.setBackgroundResource(R.drawable.tabackgrnd)
                amount2TV.setTextColor(resources.getColor(R.color.black))
                amount4TV.setBackgroundResource(R.drawable.tabackgrnd)
                amount4TV.setTextColor(resources.getColor(R.color.black))
                tipamount=0
                isTipEnter=false

            }
        })
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()
    }

    private fun submitTip(tipamount: Int, review: String) {
        progressbarLL.visibility = View.VISIBLE
        tipViewModel.tipObserver().removeObservers(this)
        tipViewModel.tipObserver().observe(this) {
            if (it?.status == true) {
                Utils.showToast("Thank you for your tip",this)
                progressbarLL.visibility = View.GONE

            } else {
                progressbarLL.visibility = View.GONE
            }
        }
        tipViewModel.tipSubmission(this,storeid,orderid, review, tipamount.toString() )
    }

    fun  openCurbSideDialog()
    {
        val dialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.item_curbside_pickup, null)
        val backbtn = view.findViewById<TextView>(R.id.backbtn)
        val iamherebtn = view.findViewById<Button>(R.id.iamherebtn)

        backbtn.setOnClickListener { dialog.dismiss() }
        iamherebtn.setOnClickListener {
            dialog.dismiss()
        }

        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()

    }


}