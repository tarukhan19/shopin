package com.app.shopin.Orders.views.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.Orders.Adapter.OrderHistoryListAdapter
import com.app.shopin.Orders.viewmodels.OrderHistoryListViewModel
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ActivityOrderHistoryListBinding
import com.app.shopin.homePage.views.Activity.CartPageActivity
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.view.OptionsPickerView
import kotlinx.android.synthetic.main.activity_order_history_list.*
import kotlinx.android.synthetic.main.toolbar.view.*
import kotlinx.android.synthetic.main.toolbar.view.back_LL
import kotlinx.android.synthetic.main.toolbar.view.titleTV
import kotlinx.android.synthetic.main.toolbar_order_list.view.*

class OrderHistoryListActivity : AppCompatActivity() ,View.OnClickListener{
    lateinit var binding:ActivityOrderHistoryListBinding
    lateinit var orderHistoryListViewModel: OrderHistoryListViewModel
    lateinit var  orderHistoryListAdapter:OrderHistoryListAdapter
    lateinit var pvOptions: OptionsPickerView<Any>
//    lateinit var twoPicker: MyOptionsPickerView<Any>
    val durationlist=ArrayList<Any>()
    var orderstatusArrayList=ArrayList<Any>()
    var orderstatusIdArrayList=ArrayList<Any>()
    val durationlistId=ArrayList<Any>()

    var selectDuration=""
    var selectDurationId=""
    var selectOrderStatus=""
    var selectOrderStatusId=""

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
            R.id.addtocartIV -> {
                val in7 = Intent(this, CartPageActivity::class.java)
                startActivity(in7)
                overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
            }
            R.id.filterIV -> {
                pvOptions.show()
            }
        }
    }

    fun initialize()
    {
        orderHistoryListViewModel= ViewModelProvider(this).get(OrderHistoryListViewModel::class.java)
        toolbar.titleTV.text=getString(R.string.orders)
        toolbar.back_LL.setOnClickListener(this)
        toolbar.addtocartIV.setOnClickListener(this)
        toolbar.filterIV.setOnClickListener(this)

        durationlist.add("3 Months")
        durationlist.add("6 Months")
        durationlist.add("12 Months")

        durationlistId.add("3")
        durationlistId.add("6")
        durationlistId.add("12")

        loadOrderHistoryList()

    }

    fun loadOrderHistoryList()
    {
        orderHistoryListViewModel.getAllStoreListObserver().removeObservers(this)
        orderHistoryListViewModel.getAllStoreListObserver()
            .observe(this) {
                if (it != null) {

                    val orderlist= it.data.order_list!!
                    val orderstatusList=it.data.status_list

                    Log.e("orderlistsize",orderlist.size.toString())
                    if (selectOrderStatus.isEmpty())
                    {
                        for (i in 0 until orderstatusList.size-1)
                        {
                            orderstatusArrayList.add(orderstatusList[i].value)
                            orderstatusIdArrayList.add(orderstatusList[i].key)
                        }

                        initOptionPicker()

                    }


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

        orderHistoryListViewModel.getAllOrderHistoryList(this,selectDurationId,selectOrderStatusId)
    }



    private fun initOptionPicker() {
        pvOptions = OptionsPickerBuilder(
            this
        ) { options1, options2, options3, v ->
            val str = """
        ${durationlist.get(options1)}
        ${orderstatusArrayList.get(options2)}
        """.trimIndent()
        }
            .setOptionsSelectChangeListener { options1, options2, options3 ->
                selectDuration = durationlist[options1].toString()
                selectDurationId=durationlistId[options1].toString()
                selectOrderStatus= orderstatusArrayList[options2].toString()
                selectOrderStatusId=orderstatusIdArrayList[options2].toString()
//                val str = selectDurationId+" "+selectOrderStatusId.toString()
//                Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
            }
            .setItemVisibleCount(5)
            .setSelectOptions(0, 0, 0)
            .setTitleText("Filter")
            .setContentTextSize(14)
            .addOnSubmitClickListener {

                loadOrderHistoryList()
            }
            .build<Any>()
        pvOptions.setNPicker(durationlist, orderstatusArrayList, null)


    }
}