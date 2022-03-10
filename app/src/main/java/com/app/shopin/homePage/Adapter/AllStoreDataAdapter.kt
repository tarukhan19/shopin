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
import com.app.shopin.databinding.ItemStoreDataBinding
import com.app.shopin.homePage.models.AllStoreDataValues
import com.app.shopin.homePage.models.StoreInventoryData
import com.app.shopin.homePage.views.Activity.ProductDetailActivity
import com.app.shopin.homePage.views.Activity.StoreDetailActivity
import com.app.shopin.utils.Constant
import com.app.shopin.utils.DistanceCalculationMethod
import com.app.shopin.utils.Preference
import java.lang.Exception

class AllStoreDataAdapter(

    var allStoreDataValues: ArrayList<AllStoreDataValues>,
    var isSearch:Boolean


) : RecyclerView.Adapter<AllStoreDataAdapter.MyViewHolder>() {
    lateinit var binding: ItemStoreDataBinding
    var activity: Activity? = null
    lateinit var ctx: Context

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        ctx = parent.context
        activity = ctx as Activity

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.item_store_data, parent, false
        )
        return MyViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = allStoreDataValues[position]

        binding.allstoredata = data
        binding.executePendingBindings()
        val originlat= Preference.getInstance(ctx)?.getString(Constant.CURRENT_LOCATION_LAT)?.toDouble()
        val originlng= Preference.getInstance(ctx)?.getString(Constant.CURRENT_LOCATION_LONG)?.toDouble()
        val destlat=data.lattitude?.toDouble()
        val destlng=data.longitude?.toDouble()

        val key= Preference.getInstance(ctx)?.getString(Constant.EXTERNAL_SEARCH_FILTER)!!
        if (key.equals("1"))
        {
            binding.productLL.visibility= View.GONE
        }
        else
        {
            binding.productLL.visibility= View.VISIBLE
            try {
                val storeInventoryData:ArrayList<StoreInventoryData>
                Log.e("isSearch",isSearch.toString())

                if (isSearch)
                {
                    storeInventoryData= data.inventory_items!!
                    Log.e("storeInventoryData",storeInventoryData.size.toString())

                }
                else
                {
                    storeInventoryData= data.store_inventory!!
                    Log.e("storeInventoryData",storeInventoryData.size.toString())

                }


                val adapter = SearchProductAdapter(ctx, storeInventoryData!!)
                val layoutManager = LinearLayoutManager(ctx)
                binding.productRV.setHasFixedSize(true)
                binding.productRV.layoutManager = layoutManager
                binding.productRV.adapter = adapter
                adapter.notifyDataSetChanged()
            }
            catch (e: Exception){
                Log.e("exce",e.message.toString())
            }
        }

        if (data.rating.equals("0.0"))
        {
            binding.ratingLL.setBackgroundResource(R.drawable.ratinggrayback)
        }
        else
        {
            binding.ratingLL.setBackgroundResource(R.drawable.ratingyellowback)

        }

        binding.storeLL.setOnClickListener(View.OnClickListener {
            val intent = Intent(ctx, StoreDetailActivity::class.java)
            intent.putExtra("id",data.id)
            ctx.startActivity(intent)
        })
        DistanceCalculationMethod.getDistance(originlat!!,
            originlng!!, destlat!!, destlng!!,ctx,binding.distanceTV)
    }

    override fun getItemCount(): Int {
        return allStoreDataValues.size
    }


    class MyViewHolder(itemView: ItemStoreDataBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemStoreDataBinding

        init {
            binding = itemView
        }
    }

}
