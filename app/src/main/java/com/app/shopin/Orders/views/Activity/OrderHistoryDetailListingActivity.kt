package com.app.shopin.Orders.views.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shopin.Orders.Adapter.StoreItemHistoryAdapter
import com.app.shopin.Orders.viewmodels.OrderHistoryViewModel
import com.app.shopin.Orders.viewmodels.RatingReviewViewModel
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ActivityOrderHistoryDetailListingBinding
import com.app.shopin.homePage.models.CartChildData
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_order_history_detail_listing.*
import kotlinx.android.synthetic.main.activity_order_history_list.toolbar
import kotlinx.android.synthetic.main.toolbar.view.*
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
        toolbar.titleTV.text = getString(R.string.orderhsitory)
        toolbar.back_LL.setOnClickListener(this)
        ratingRB.setOnTouchListener(View.OnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                openRatingReviewDialog()
            }
            return@OnTouchListener true
        })
        binding.progressbarLL.visibility = View.VISIBLE
        binding.buttonTV1.setOnClickListener(this)
        loadOrderHistoryDetails()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back_LL -> {
                finish()
            }
            R.id.buttonTV1 -> {
                //openRatingReviewDialog()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadOrderHistoryDetails() {
        orderHistoryViewModel.getAllStoreListObserver().removeObservers(this)
        orderHistoryViewModel.getAllStoreListObserver()
            .observe(this) {
                if (it != null) {
                    if (it.status_code == 200) {
                        binding.cardview.visibility = View.VISIBLE
                        binding.progressbarLL.visibility = View.GONE
                        val data = it.data.order_drtail
                        storeid= data.id!!
                        val storedata = it.data.order_drtail
                        val storeratedata = it.data.order_drtail.store_rating


                        binding.orderidTV.setText(data.order_no)
                        dateFormat(data.created_date, binding.dateTV)
                        binding.storenameTV.setText(storedata.store_name)
                        Utils.setImage(binding.shopIV, storedata.store_image, R.drawable.store)
                        store_order_type = storedata.store_order_type
                        binding.buttonTV1.text = store_order_type
                        binding.totalpriceTV.text = "$ " + data.total_amount
                        binding.ratingTV.setText(storeratedata?.rating__avg)
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
                            var overalltotal=0.0
                            var total=0.0

                            for (i in 0..storeInventoryData.size-1)
                            {
                                 overalltotal = storeInventoryData[i].price+overalltotal
                            }
                            total=overalltotal+total

                            binding.totalpriceTV.text="$ "+total.toString()
                        } catch (e: Exception) {
                            Log.e("exce", e.message.toString())
                        }


                    } else {
                        binding.progressbarLL.visibility = View.GONE

                    }


                }
            }

        orderHistoryViewModel.getAllOrderHistoryList(this, orderid)
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

        backbtn.setOnClickListener { dialog.dismiss() }
        submitBTN.setOnClickListener {
            val rating=ratingBar.rating.toString()
            val review=reviewET.text.toString()
            Log.e("rating",rating)
            dialog.dismiss()


            binding.progressbarLL.visibility=View.VISIBLE
            reviewRatingSubmit(storeid ,rating,review)


        }

        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()


    }

    private fun reviewRatingSubmit(
        id: String,
        rating: String,
        review: String
    )
    {
        progressbarLL.visibility = View.VISIBLE
        ratingReviewViewModel.getRatingReviewObserver().removeObservers(this)
        ratingReviewViewModel.getRatingReviewObserver().observe(this) {

            if (it?.status == true && it.status_code == 200) {
                progressbarLL.visibility = View.GONE
            } else {
                progressbarLL.visibility = View.GONE
            }
        }
        ratingReviewViewModel.ratingReviewSubmission(this,id,rating,review)
    }

}