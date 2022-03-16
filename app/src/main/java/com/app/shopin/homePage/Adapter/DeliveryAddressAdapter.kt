package com.app.shopin.homePage.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ItemDeliveryAddressListBinding
import com.app.shopin.homePage.models.DeliveryAddressData
import com.app.shopin.homePage.views.Activity.DeliveryAddressUpdateActivity

class DeliveryAddressAdapter(
    var ctx: Context) : RecyclerView.Adapter<DeliveryAddressAdapter.MyViewHolder>() {
    lateinit var binding: ItemDeliveryAddressListBinding
    var activity: Activity? = null
    private var deliveryAddressListData = ArrayList<DeliveryAddressData>()
    fun setListData(data: ArrayList<DeliveryAddressData>) {
        this.deliveryAddressListData = data
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        ctx = parent.context
        activity = ctx as Activity
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.item_delivery_address_list, parent, false
        )
        return MyViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = deliveryAddressListData[position]
        if (data.is_default!!)
        {
            holder.binding.defaultBTN.visibility=View.VISIBLE
        }
        else
        {
            holder.binding.defaultBTN.visibility=View.INVISIBLE
        }
        if (data.address_type.equals("Home"))
        {
            holder.binding.addresstypeIV.setImageResource(R.drawable.house)
        }
        else
        {
            holder.binding.addresstypeIV.setImageResource(R.drawable.officebuilding)
        }
        holder.binding.editLL.setOnClickListener {
            val in7 = Intent(ctx, DeliveryAddressUpdateActivity::class.java)
            in7.putExtra("id",data.id.toString())
            ctx.startActivity(in7)
        }
        holder.binding.recyclerdata = data
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return deliveryAddressListData.size
    }

    class MyViewHolder(itemView: ItemDeliveryAddressListBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemDeliveryAddressListBinding = itemView

    }

}

