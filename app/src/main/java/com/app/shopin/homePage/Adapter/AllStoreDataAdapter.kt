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
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_issue_with_items.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.json.JSONException
import org.json.JSONObject
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

        val originlat= Preference.getInstance(ctx)?.getString(Constant.CURRENT_LOCATION_LAT)?.toDouble()
        val originlng= Preference.getInstance(ctx)?.getString(Constant.CURRENT_LOCATION_LONG)?.toDouble()
        val destlat=data.latitude?.toDouble()
        val destlng=data.longitude?.toDouble()
        holder.binding.nameTV.text=data.name

//        val storerating = data.rating_details
//        val gson = Gson()
//        val jsonString = gson.toJson(storerating)
//        var orderrateJson = JSONObject()
//
//        try {
//            orderrateJson = JSONObject(jsonString)
//            if (!orderrateJson.equals("{}")) {
//                val avgrating = orderrateJson.optDouble("ratining_avg")
//                val reviewcount = orderrateJson.optString("total_review")
//                Log.e("reviewcount",avgrating.toString())
//                holder.binding.reviewcountTV.setText("$reviewcount Reviews")
//
//                if (avgrating.toString().equals("NaN")) {
//                    holder.binding.ratingTV.setText("0.0")
//                } else {
//                    holder.binding.ratingTV.setText(String.format("%.2f", avgrating))
//                }
//
//                if (avgrating.toString().equals("0.0")) {
//                    holder.binding.ratingLL.setBackgroundResource(R.drawable.ratinggrayback)
//                } else {
//                    holder.binding.ratingLL.setBackgroundResource(R.drawable.ratingyellowback)
//                }
//            }
//        } catch (e: JSONException) {
//            e.printStackTrace()
//        }


        val key= Preference.getInstance(ctx)?.getString(Constant.EXTERNAL_SEARCH_FILTER)!!
        if (key.equals("1"))
        {
            holder.binding.productLL.visibility= View.GONE
        }
        else
        {
            holder.binding.productLL.visibility= View.VISIBLE

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
                holder.binding.productRV.setHasFixedSize(true)
                holder.binding.productRV.isNestedScrollingEnabled = false

                holder.binding.productRV.layoutManager = layoutManager
                holder.binding.productRV.adapter = adapter
                adapter.notifyDataSetChanged()
            }
            catch (e: Exception){
                Log.e("exce",e.message.toString())
            }
        }

        if (data.rating.equals("0.0"))
        {
            holder.binding.ratingLL.setBackgroundResource(R.drawable.ratinggrayback)
        }
        else
        {
            holder.binding.ratingLL.setBackgroundResource(R.drawable.ratingyellowback)

        }

        holder.binding.storeLL.setOnClickListener(View.OnClickListener {
            val intent = Intent(ctx, StoreDetailActivity::class.java)
            intent.putExtra("id",data.id)
            ctx.startActivity(intent)
        })
        try {
            DistanceCalculationMethod.getDistance(originlat!!,
                originlng!!, destlat!!, destlng!!,ctx,holder.binding.distanceTV)
        }
        catch (e:Exception)
        {
            Log.e("distexcep",e.message.toString())
        }

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
