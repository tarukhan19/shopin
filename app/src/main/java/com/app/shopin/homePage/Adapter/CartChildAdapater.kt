package com.app.shopin.homePage.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ItemCartChildBinding
import com.app.shopin.homePage.models.CartChildData
import com.app.shopin.homePage.viewmodels.RemoveCartViewModel
import com.app.shopin.homePage.views.Activity.CartPageActivity

class CartChildAdapater(
    var cartChildData: ArrayList<CartChildData>,
    var parentholder: CartParentAdapater.MyViewHolder,
    var progressbarLL: LinearLayout,
    ) : RecyclerView.Adapter<CartChildAdapater.MyViewHolder>() {
    lateinit var binding: ItemCartChildBinding
    var ctx:Context?=null
    private lateinit var removeCartViewModel: RemoveCartViewModel

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        ctx = parent.context

        binding = DataBindingUtil.inflate(
            LayoutInflater.from(ctx),
            R.layout.item_cart_child, parent, false
        )
        return MyViewHolder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = cartChildData[position]
        removeCartViewModel = ViewModelProvider(ctx as CartPageActivity).get(RemoveCartViewModel::class.java)

        holder.binding.prodnameTV.text=data.inventory_name
        holder.binding.prodamountTV.text="$"+data.price+"/pc"
        holder.binding.deleteLL.setOnClickListener{
            if (cartChildData.size==1)
            {
                progressbarLL.visibility=View.VISIBLE
                val pos = parentholder.adapterPosition
                CartParentAdapater.getInstance()?.runThread(pos)
                removeCart(data.inventory)
            }
            else
            {
                progressbarLL.visibility=View.VISIBLE

                cartChildData.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged (position, itemCount);
                removeCart(data.inventory)

            }
        }

        holder.binding.quantityTV.text=data.quantity


    }

    override fun getItemCount(): Int {
        return cartChildData.size
    }


    class MyViewHolder(itemView: ItemCartChildBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemCartChildBinding

        init {
            binding = itemView
        }
    }



    private fun removeCart(id: String?)
    {
        removeCartViewModel.removecartviewmodel.removeObservers(ctx as CartPageActivity )
        removeCartViewModel.getObserveData().observe(ctx as CartPageActivity) {

            if (it?.status == true ) {
                progressbarLL.visibility=View.GONE

                Toast.makeText(ctx, "Removed", Toast.LENGTH_SHORT).show()
            } else {
                progressbarLL.visibility=View.GONE

            }
        }
        removeCartViewModel.removeCartResponse(
            ctx as CartPageActivity, id!!
        )
    }
}
