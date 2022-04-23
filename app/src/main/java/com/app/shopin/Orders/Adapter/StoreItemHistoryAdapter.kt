package com.app.shopin.Orders.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.Orders.views.Activity.IssueWithItemsActivity
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ItemProdHistoryBinding
import com.app.shopin.homePage.models.CartChildData
import com.app.shopin.utils.Constant
import java.lang.Exception

class StoreItemHistoryAdapter(
    var ctx: Context,
    var cartChildData: ArrayList<CartChildData>,
    var order_no: String
) : RecyclerView.Adapter<StoreItemHistoryAdapter.MyViewHolder>() {
    lateinit var binding: ItemProdHistoryBinding
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.item_prod_history, parent, false
        )
        return MyViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = cartChildData[position]

        holder.binding.prodnameTV.text=data.inventory_name
        holder.binding.prodamountTV.text="$"+data.price+"/pc"
        if (data.item_issue)
        {
            holder.binding.checkissuestatusLL.visibility=View.VISIBLE
        }
        else
        {
            holder.binding.checkissuestatusLL.visibility=View.GONE

        }

        try {
            Log.e("image", Constant.IMAGE_BASE_URL+data.inventory_image)

            Utils.setImage(holder.binding.productIV, Constant.IMAGE_BASE_URL+data.inventory_image,R.drawable.freshys)
        }
        catch (e:Exception){}
        holder.binding.checkissuestatusLL.setOnClickListener {
            val in7 = Intent(ctx, IssueWithItemsActivity::class.java)
            in7.putExtra("order_no",order_no)
            ctx.startActivity(in7)
        }
        if (data.quantity.equals("1"))
        {
            holder.binding.quantityTV.text=data.quantity +" Item"
        }
        else
        {
            holder.binding.quantityTV.text=data.quantity +" Items"
        }

    }

    override fun getItemCount(): Int {
        return cartChildData.size
    }


    class MyViewHolder(itemView: ItemProdHistoryBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemProdHistoryBinding

        init {
            binding = itemView
        }
    }


}
