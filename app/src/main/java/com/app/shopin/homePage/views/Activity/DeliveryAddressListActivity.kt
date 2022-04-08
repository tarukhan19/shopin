package com.app.shopin.homePage.views.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shopin.homePage.models.DeliveryAddressData
import com.app.shopin.homePage.models.DeliveryAddressListResponse
import com.app.shopin.homePage.viewmodels.DeliveryAddressListViewModel
import com.app.shopin.R
import com.app.shopin.databinding.ActivityDeliveryAddressListBinding
import kotlinx.android.synthetic.main.activity_delivery_address_list.*
import kotlinx.android.synthetic.main.toolbar.view.*

class DeliveryAddressListActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityDeliveryAddressListBinding
    lateinit var deliveryAddressListViewModel: DeliveryAddressListViewModel
     var addressList= ArrayList<DeliveryAddressData>()
    companion object
    {
        @SuppressLint("StaticFieldLeak")
        var deliveryAddressListActivity: DeliveryAddressListActivity? = null
        fun getInstance(): DeliveryAddressListActivity? {
            return deliveryAddressListActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_delivery_address_list)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addAddressTV -> {
                val in7 = Intent(this, DeliveryAddressAddActivity::class.java)
                in7.putExtra("addresslist",addressList.size.toString())
                startActivity(in7)
            }
            R.id.back_LL -> {
                finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun getAllAddress() {
        deliveryAddressListViewModel.getDeliveryAddressObserver().observe(this, Observer<DeliveryAddressListResponse> {
            if (it != null) {
                if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    addressList = it.data.customer_address!!
                    Log.e("addressList",addressList?.size.toString())
                    if (addressList?.size!=0) {
                        deliveryAddressRecycler.visibility=View.VISIBLE
                        norecrdfoundTV.visibility=View.GONE
                        deliveryAddressListViewModel.setAdapter(addressList!!)
                    }
                    else
                    {
                        deliveryAddressRecycler.visibility=View.GONE
                        norecrdfoundTV.visibility=View.VISIBLE

                    }
                }
            } else {
                Toast.makeText(this, "Something wrong with DeliveryAddress Api", Toast.LENGTH_SHORT)
                    .show()
            }
        })
        deliveryAddressListViewModel.getDeliveryAddressList(this)
    }


    private fun initialize()
    {
        deliveryAddressListActivity=this
        deliveryAddressListViewModel = ViewModelProvider(this).get(DeliveryAddressListViewModel::class.java)
        binding.itemViewModel = deliveryAddressListViewModel
        toolbar.titleTV.text=getString(R.string.delivAddresList)
        toolbar.back_LL.setOnClickListener(this)
        addAddressTV.setOnClickListener(this)
        deliveryAddressRecycler.apply {
            deliveryAddressRecycler.setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@DeliveryAddressListActivity)
        }


    }

    override fun onResume() {
        super.onResume()
        getAllAddress()
    }

    fun runThread() {
        object : Thread() {
            @SuppressLint("NotifyDataSetChanged")
            override fun run() {
                try {
                    runOnUiThread {
                        CartPageActivity.getInstance()?.runThread("addressselect","")
                        finish()
                    }
                    sleep(300)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }


}


