package com.app.shopin.homePage.Adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.customview.RegularTextView
import com.app.shopin.databinding.ItemCartParentBinding
import com.app.shopin.homePage.models.CartChildData
import com.app.shopin.homePage.models.CartParentData
import com.app.shopin.homePage.views.Activity.CartPageActivity
import java.lang.Exception

class CartParentAdapater(

    var allStoreDataValues: ArrayList<CartParentData>?,
    var norecrdfoundTV: RegularTextView,
    var recycleview: RecyclerView,

    ) : RecyclerView.Adapter<CartParentAdapater.MyViewHolder>()  {
    lateinit var binding: ItemCartParentBinding
    var activity: Activity? = null
    var context:Context?=null

    companion object {
        @SuppressLint("StaticFieldLeak")
        var cartParentAdapater: CartParentAdapater? = null
        fun getInstance(): CartParentAdapater? {
            return cartParentAdapater
        }
    }

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        activity = context as Activity
        cartParentAdapater = this
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.item_cart_parent, parent, false
        )
        return MyViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = allStoreDataValues!![position]
        holder.binding.shopnametv.text = data.name
        holder.binding.addressTV.text = data.address
        try {
            val storeInventoryData: ArrayList<CartChildData> = data.cart_item!!
            val adapter = CartChildAdapater(storeInventoryData,holder)
            val layoutManager = LinearLayoutManager(context)
            binding.productRV.setHasFixedSize(true)
            binding.productRV.layoutManager = layoutManager
            binding.productRV.adapter = adapter
            adapter.notifyDataSetChanged()
        } catch (e: Exception) {
            Log.e("exce", e.message.toString())
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

    fun runThread(pos:Int)
    {
        object : Thread() {
            @SuppressLint("NotifyDataSetChanged")
            override fun run() {
                try
                {
                    (context as Activity).runOnUiThread {
                        allStoreDataValues?.removeAt(pos)
                        notifyItemRemoved(pos)

                        if (allStoreDataValues!!.size==0)
                        {
                            recycleview.visibility=View.GONE
                            norecrdfoundTV.visibility=View.VISIBLE
                        }
                    }
                    sleep(300)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }

}
