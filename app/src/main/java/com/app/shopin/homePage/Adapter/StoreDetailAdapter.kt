package com.app.shopin.homePage.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.databinding.ItemStoreCategoryBinding
import com.app.shopin.homePage.models.StoreCategoryData
import com.app.shopin.homePage.models.StoreInventoryData

class StoreDetailAdapter(
    var ctx: Context,
    var storeCategoryData: ArrayList<StoreCategoryData>) : RecyclerView.Adapter<StoreDetailAdapter.MyViewHolder>() {
    lateinit var binding: ItemStoreCategoryBinding
    var activity: Activity? = null
    lateinit var storeDetailItemAdapter:StoreDetailItemAdapter

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        ctx = parent.context
        activity = ctx as Activity

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.item_store_category, parent, false
        )
        return MyViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val storeInventoryData = storeCategoryData[position]

        holder.binding.categorynameTV.text = storeInventoryData.value

        val allStoreDataList: ArrayList<StoreInventoryData>? = storeInventoryData.inventory_items

        binding.prodRV.apply {
            binding.prodRV.setHasFixedSize(true)
            binding.prodRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL ,false)
        }

        storeDetailItemAdapter = StoreDetailItemAdapter(ctx, allStoreDataList!!)
        binding.prodRV.adapter = storeDetailItemAdapter
        storeDetailItemAdapter.notifyDataSetChanged()


    }

    override fun getItemCount(): Int {
        return storeCategoryData.size
    }


    class MyViewHolder(itemView: ItemStoreCategoryBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemStoreCategoryBinding

        init {
            binding = itemView
        }
    }

}
