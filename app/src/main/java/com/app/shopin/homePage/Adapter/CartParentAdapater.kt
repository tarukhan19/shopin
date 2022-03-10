package com.app.shopin.homePage.Adapter

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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.databinding.ItemCartParentBinding
import com.app.shopin.homePage.models.AllStoreDataValues
import com.app.shopin.homePage.models.StoreInventoryData
import com.app.shopin.utils.Constant
import com.app.shopin.utils.DistanceCalculationMethod
import com.app.shopin.utils.Preference
import java.lang.Exception

class CartParentAdapater(

    var allStoreDataValues: ArrayList<AllStoreDataValues>?,

    ) : RecyclerView.Adapter<CartParentAdapater.MyViewHolder>() {
    lateinit var binding: ItemCartParentBinding
    var activity: Activity? = null
    lateinit var ctx: Context

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        ctx = parent.context
        activity = ctx as Activity

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.item_cart_parent, parent, false
        )
        return MyViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = allStoreDataValues!![position]
        holder.binding.shopnametv.text=data.name
        holder.binding.addressTV.text=data.address

            try {
                val storeInventoryData:ArrayList<StoreInventoryData>

                    storeInventoryData= data.cart_item!!
                    Log.e("storeInventoryData",storeInventoryData.size.toString())


//
//                val adapter = SearchProductAdapter(ctx, storeInventoryData!!)
//                val layoutManager = LinearLayoutManager(ctx)
//                binding.productRV.setHasFixedSize(true)
//                binding.productRV.layoutManager = layoutManager
//                binding.productRV.adapter = adapter
//                adapter.notifyDataSetChanged()
            }
            catch (e: Exception){
                Log.e("exce",e.message.toString())
            }



    }

    override fun getItemCount(): Int {
        return allStoreDataValues!!.size
    }


    class MyViewHolder(itemView: ItemCartParentBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemCartParentBinding

        init {
            binding = itemView
        }
    }

}
