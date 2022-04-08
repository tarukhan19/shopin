package com.app.shopin.Orders.views.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.databinding.ActivityOrderHistoryListBinding
import com.app.shopin.Orders.Adapter.OrderHistoryListAdapter
import com.app.shopin.Orders.viewmodels.OrderHistoryListViewModel
import kotlinx.android.synthetic.main.activity_cart_page.*
import kotlinx.android.synthetic.main.activity_order_history_list.*
import kotlinx.android.synthetic.main.activity_order_history_list.norecrdfoundTV
import kotlinx.android.synthetic.main.activity_order_history_list.recycleview
import kotlinx.android.synthetic.main.activity_order_history_list.toolbar
import kotlinx.android.synthetic.main.toolbar.view.*

class OrderHistoryListActivity : AppCompatActivity() ,View.OnClickListener{
    lateinit var binding:ActivityOrderHistoryListBinding
    lateinit var orderHistoryListViewModel: OrderHistoryListViewModel
    lateinit var  orderHistoryListAdapter:OrderHistoryListAdapter
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_order_history_list)
        initialize()
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.back_LL -> {
                finish()
            }
        }
    }

    fun initialize()
    {
        orderHistoryListViewModel= ViewModelProvider(this).get(OrderHistoryListViewModel::class.java)
        toolbar.titleTV.text=getString(R.string.orders)
        toolbar.back_LL.setOnClickListener(this)
        loadOrderHistoryList()

    }

    fun loadOrderHistoryList()
    {
        orderHistoryListViewModel.getAllStoreListObserver().removeObservers(this)
        orderHistoryListViewModel.getAllStoreListObserver()
            .observe(this) {
                if (it != null) {


                    val orderlist= it.data.order_list!!

                    if (orderlist.size == 0) {
                        recycleview.visibility = View.GONE
                        norecrdfoundTV.visibility = View.VISIBLE
                    } else {
                        recycleview.visibility = View.VISIBLE
                        norecrdfoundTV.visibility = View.GONE

                    }
                    orderHistoryListAdapter = OrderHistoryListAdapter(orderlist,this)

                    recycleview.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    recycleview.adapter = orderHistoryListAdapter


                }
            }

        orderHistoryListViewModel.getAllOrderHistoryList(this)
    }
}