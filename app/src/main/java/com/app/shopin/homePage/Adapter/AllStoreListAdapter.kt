package com.app.shopin.homePage.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ItemAllstoreDataBinding
import com.app.shopin.homePage.models.AllStoreCategoryData
import com.app.shopin.homePage.models.AllStoreDataValues
import com.app.shopin.homePage.viewmodels.AllStoreListViewModel
import kotlinx.android.synthetic.main.fragment_store.*

class AllStoreListAdapter : RecyclerView.Adapter<AllStoreListAdapter.ViewHolder>() {
    private var searchCategoryArrayListData = ArrayList<AllStoreCategoryData>()
    lateinit var allStoreListViewModel: AllStoreListViewModel

    fun setListData(data: ArrayList<AllStoreCategoryData>, context: FragmentActivity) {
        this.searchCategoryArrayListData = data
        allStoreListViewModel = ViewModelProvider(context).get(AllStoreListViewModel::class.java)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAllstoreDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(searchCategoryArrayListData[position],allStoreListViewModel)

    }

    class ViewHolder(var binding: ItemAllstoreDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AllStoreCategoryData, allStoreListViewModel: AllStoreListViewModel) {
            binding.allstorerecyclerdata = data
            binding.executePendingBindings()

            val allStoreDataList: ArrayList<AllStoreDataValues>? = data.allStoreDataList.allStoreCategoryData
            if (allStoreDataList != null) {
                allStoreListViewModel.setallStoreDataAdapter(allStoreDataList)
            }

            binding.storeRV.apply {
                binding.storeRV.setHasFixedSize(true)
                binding.storeRV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
            }



        }
    }
    override fun getItemCount(): Int {
        return searchCategoryArrayListData.size
    }

}