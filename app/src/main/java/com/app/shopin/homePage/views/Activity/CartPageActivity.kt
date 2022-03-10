package com.app.shopin.homePage.views.Activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shopin.R
import com.app.shopin.databinding.ActivityCartPageBinding
import com.app.shopin.homePage.Adapter.CartParentAdapater
import com.app.shopin.homePage.viewmodels.CartListViewModels
import kotlinx.android.synthetic.main.activity_cart_page.*
import kotlinx.android.synthetic.main.activity_cart_page.toolbar
import kotlinx.android.synthetic.main.toolbar.view.*

class CartPageActivity : AppCompatActivity() {
    lateinit var binding:ActivityCartPageBinding
    lateinit var cartListViewModels: CartListViewModels
    lateinit var cartParentAdapater: CartParentAdapater

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart_page)
        initialize()

    }
    @SuppressLint("NotifyDataSetChanged")
    private fun initialize()
    {
        cartListViewModels =
            ViewModelProvider(this).get(CartListViewModels::class.java)
        binding.lifecycleOwner = this
        toolbar.titleTV.text = "Cart"
        recycleview.apply {
            recycleview.setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@CartPageActivity)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchCartData()
    {
        cartListViewModels.getObserveData().removeObservers(this)
        cartListViewModels.getObserveData()
            .observe(this) {
                if (it != null)
                {
                    var storelist=it.data.store
                    cartParentAdapater = CartParentAdapater(storelist)
                    recycleview.adapter = cartParentAdapater
                    cartParentAdapater.notifyDataSetChanged()

                }
            }

        cartListViewModels.cartListResponse(this)
    }

    override fun onResume() {
        super.onResume()
        fetchCartData()
    }
}