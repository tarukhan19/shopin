package com.app.shopin.homePage.Adapter

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.databinding.ItemSearchProductBinding
import com.app.shopin.homePage.models.StoreInventoryData

class SearchProductAdapter(
    var ctx: Context,
    var storeInventoryDataList: ArrayList<StoreInventoryData>,


    ) : RecyclerView.Adapter<SearchProductAdapter.MyViewHolder>() {
    lateinit var binding: ItemSearchProductBinding
    var activity: Activity? = null


    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        ctx = parent.context
        activity = ctx as Activity

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.item_search_product, parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val storeInventoryData = storeInventoryDataList[position]

        Log.e("storeInventoryData.name",storeInventoryData.name.toString())
        holder.binding.productnameTV.text = storeInventoryData.name+" fall rescue ("+ storeInventoryData.size+ storeInventoryData.size_unit+")"
        holder.binding.productdescTV.text =  storeInventoryData.name+" fall rescue ("+ storeInventoryData.size+ storeInventoryData.size_unit+")"
        holder.binding.productspTV.text = "$"+storeInventoryData.price+"/pc"
        holder.binding.productstrikepTV.text = "$"+storeInventoryData.price+"/pc"
        holder.binding.productstrikepTV.setPaintFlags(holder.binding.productstrikepTV.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

    }

    override fun getItemCount(): Int {
        return storeInventoryDataList.size
    }


    class MyViewHolder(itemView: ItemSearchProductBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemSearchProductBinding

        init {
            binding = itemView
        }
    }

}
