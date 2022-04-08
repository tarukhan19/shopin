package com.app.shopin.Orders.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.databinding.ItemOrderHistoryListBinding
import com.app.shopin.Orders.models.OrderHistoryListDataValues
import com.app.shopin.Orders.views.Activity.OrderHistoryDetailListingActivity
import com.app.shopin.customview.RegularTextView
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderHistoryListAdapter(
    var allStoreDataValues: ArrayList<OrderHistoryListDataValues>,
    var ctx:Context) : RecyclerView.Adapter<OrderHistoryListAdapter.MyViewHolder>() {
    lateinit var binding: ItemOrderHistoryListBinding
    var activity: Activity? = null

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        ctx = parent.context
        activity = ctx as Activity
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.item_order_history_list, parent, false
        )

        return MyViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
         val data = allStoreDataValues[position]
        dateFormat(data.updated_date,holder.binding.delvdateTV)
        dateFormat(data.created_date,holder.binding.orderdateTV)
        holder.binding.orderidTV.setText(data.order_no)

        holder.binding.cardview.setOnClickListener {
            Log.e("orderid",data.id.toString())
            val in7 = Intent(ctx, OrderHistoryDetailListingActivity::class.java)
            in7.putExtra("orderid",data.id)

            ctx.startActivity(in7)
        }

        try {
            val orderList=data.order_item[0]
            holder.binding.prodnameTV.setText(orderList.inventory_name)
        }
        catch (e:Exception)
        {
            Log.e("orderListexc",e.localizedMessage)
        }

        val count = data.item_count?.minus(1)
        Log.e("count",count.toString())
        holder.binding.prodcountTV.setText(count.toString()+ " more items")
        holder.binding.orderstatusTV.setText(data.order_status)

        if (count==0)
        {
            holder.binding.prodcountTV.visibility=View.GONE
        }
        else
        {
            holder.binding.prodcountTV.visibility=View.VISIBLE
        }

        if (data.order_status.equals("Delivered"))
        {
            holder.binding.orderstatusTV.setBackgroundResource(R.drawable.ord_deliv_capsule)
        }
        else   if (data.order_status.equals("Pending") || data.order_status.equals("New"))
        {
            holder.binding.orderstatusTV.setBackgroundResource(R.drawable.ord_pending_capsule)
        }
        else   if (data.order_status.equals("Cancelled"))
        {
            holder.binding.orderstatusTV.setBackgroundResource(R.drawable.ord_cancel_capsule)
        }

        else   if (data.order_status.equals("In-Process"))
        {
            holder.binding.orderstatusTV.setBackgroundResource(R.drawable.ord_inprocess_capsule)
        }


    }

    fun dateFormat(createdDate: String?, orderdateTV: RegularTextView)
    {
        try {
            var date =createdDate
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//            val output = SimpleDateFormat("dd/MM/yyyy hh:mm a")
            val output = SimpleDateFormat("EEE, d MMM, yyyy")

            var d: Date? = null
            input.setTimeZone(TimeZone.getTimeZone("UTC"));
            d = input.parse(date)
            val formatted: String = output.format(d)
            Log.i("DATE", "" + formatted)
            orderdateTV.text=formatted

        } catch (e: ParseException) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return allStoreDataValues.size
    }

    class MyViewHolder(itemView: ItemOrderHistoryListBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemOrderHistoryListBinding = itemView

    }

}
