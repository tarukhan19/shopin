package com.app.shopin.homePage.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.databinding.ItemDeliveryAddressListBinding
import com.app.shopin.homePage.models.DeliveryAddressData

class DeliveryAddressAdapter : RecyclerView.Adapter<DeliveryAddressAdapter.ViewHolder>() {
    private var deliveryAddressListData = ArrayList<DeliveryAddressData>()

    fun setListData(data: ArrayList<DeliveryAddressData>) {
        this.deliveryAddressListData = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDeliveryAddressListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(deliveryAddressListData[position])
    }

    class ViewHolder(var binding: ItemDeliveryAddressListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DeliveryAddressData) {
            if (data.is_default!!)
            {
                binding.defaultBTN.visibility=View.VISIBLE
            }
            else
            {
                binding.defaultBTN.visibility=View.INVISIBLE
            }

            if (data.address_type.equals("Home"))
            {
                binding.addresstypeIV.setImageResource(R.drawable.house)
            }
            else
            {
                binding.addresstypeIV.setImageResource(R.drawable.officebuilding)
            }
            binding.recyclerdata = data
            binding.executePendingBindings()
        }

    }
    override fun getItemCount(): Int {
        return deliveryAddressListData.size
    }

}