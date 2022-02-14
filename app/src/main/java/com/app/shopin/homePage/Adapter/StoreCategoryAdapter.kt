package com.app.shopin.homePage.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ItemSpinnerBinding
import com.app.shopin.homePage.models.StoreCategoryData

class StoreCategoryAdapter : RecyclerView.Adapter<StoreCategoryAdapter.ViewHolder>() {
    private var allStoreDataArrayList = ArrayList<StoreCategoryData>()
    lateinit var ctx: Context

    fun setListData(data: ArrayList<StoreCategoryData>, context: Context) {
        this.ctx=context
        this.allStoreDataArrayList = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(allStoreDataArrayList[position],ctx)
    }
    class ViewHolder(var binding: ItemSpinnerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoreCategoryData, ctx: Context) {
            binding.recyclerdata = data
            binding.executePendingBindings()

            binding.linearlayout.setOnClickListener {
                val listener = ctx as StoreCategoryCallback
                listener.getStoreCategory(data.value!!, data.id!!)
            }
        }



    }
    override fun getItemCount(): Int {
        return allStoreDataArrayList.size
    }

    interface StoreCategoryCallback {
        fun getStoreCategory(value: String,id : Int)
    }
}