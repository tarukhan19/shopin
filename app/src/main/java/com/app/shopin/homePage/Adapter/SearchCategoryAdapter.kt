package com.app.shopin.homePage.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ItemSearchCategoryBinding
import com.app.shopin.homePage.models.StoreCategoryData
import kotlinx.android.synthetic.main.fragment_more.*

class SearchCategoryAdapter : RecyclerView.Adapter<SearchCategoryAdapter.ViewHolder>() {
    private var searchCategoryArrayListData = ArrayList<StoreCategoryData>()
    fun setListData(data: ArrayList<StoreCategoryData>) {
        this.searchCategoryArrayListData = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSearchCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchCategoryArrayListData[position])
    }
    class ViewHolder(var binding: ItemSearchCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: StoreCategoryData) {
            binding.searchrecyclerdata = data
            binding.executePendingBindings()
            if (data.image!=null)
            {
                val profilepic= data.image
                Utils.setImage(binding.productIV, profilepic, R.drawable.welcome_cart)
            }
        }

    }
    override fun getItemCount(): Int {
        return searchCategoryArrayListData.size
    }
}