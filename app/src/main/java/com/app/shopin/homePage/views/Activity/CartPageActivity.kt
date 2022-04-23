package com.app.shopin.homePage.views.Activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shopin.R
import com.app.shopin.databinding.ActivityCartPageBinding
import com.app.shopin.homePage.Adapter.CartParentAdapater
import com.app.shopin.homePage.models.CartParentData
import com.app.shopin.homePage.viewmodels.CartListViewModels
import com.app.shopin.utils.Constant
import com.app.shopin.utils.Preference
import kotlinx.android.synthetic.main.activity_cart_page.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlin.math.log


class CartPageActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityCartPageBinding
    lateinit var cartListViewModels: CartListViewModels
    lateinit var cartParentAdapater: CartParentAdapater
    lateinit var addressid: String
    lateinit var storelist: ArrayList<CartParentData>

    companion object {
        @SuppressLint("StaticFieldLeak")
        var cartPageActivity: CartPageActivity? = null
        fun getInstance(): CartPageActivity? {
            return cartPageActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart_page)
        initialize()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initialize() {
        cartPageActivity = this
        cartListViewModels = ViewModelProvider(this).get(CartListViewModels::class.java)
        binding.lifecycleOwner = this
        toolbar.titleTV.text = "Cart"
        toolbar.back_LL.setOnClickListener(this)
        binding.adressLL.setOnClickListener(this)
        recycleview.apply {
            recycleview.setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@CartPageActivity)
        }
        fetchCartData()

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchCartData() {
        cartListViewModels.getObserveData().removeObservers(this)
        cartListViewModels.getObserveData()
            .observe(this) {
                if (it != null) {
                    Log.e("cartlistdata",it.data.toString())
                    val address = it.data?.default_address?.address
                    addressid = it.data?.default_address?.id!!
                    Preference.getInstance(this)?.setString(Constant.DELIVERY_ADDRESS, address!!)
                    Preference.getInstance(this)?.setString(Constant.DELIVERY_ADDRESS_ID, addressid)

                    addressTV.text = address
                    storelist = it.data.store!!

                    if (storelist.size == 0) {
                        recycleview.visibility = View.GONE
                        norecrdfoundTV.visibility = View.VISIBLE
                    } else {
                        recycleview.visibility = View.VISIBLE
                        norecrdfoundTV.visibility = View.GONE
                    }
                    cartParentAdapater = CartParentAdapater(
                        storelist,
                        norecrdfoundTV,
                        recycleview,
                        binding.progressbarLL,
                        this
                    )
                    recycleview.adapter = cartParentAdapater
                    cartParentAdapater.notifyDataSetChanged()

                }
            }

        cartListViewModels.cartListResponse(this)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back_LL -> {
                finish()
            }
            R.id.adressLL -> {
                val in7 = Intent(this, DeliveryAddressListActivity::class.java)
                in7.putExtra("from", "cartpage")
                startActivity(in7)
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    fun runThread(from: String, total: String) {
        runOnUiThread {
            Log.e("from",from)
            if (from.equals("cartdata")) {
                fetchCartData()
            } else if (from.equals("addressselect")) {
                addressTV.setText(
                    Preference.getInstance(this@CartPageActivity)
                        ?.getString(Constant.DELIVERY_ADDRESS)
                )

            }
        }



//        Handler(Looper.getMainLooper()).post(Runnable
//        {
//            if (from.equals("cartdata")) {
//                fetchCartData()
//            } else if (from.equals("addressselect")) {
//                addressTV.setText(
//                    Preference.getInstance(this@CartPageActivity)
//                        ?.getString(Constant.DELIVERY_ADDRESS)
//                )
//            }
//        })


    }


}