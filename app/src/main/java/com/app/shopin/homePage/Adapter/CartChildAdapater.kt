package com.app.shopin.homePage.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.databinding.ItemCartChildBinding
import com.app.shopin.homePage.models.CartChildData
import com.app.shopin.homePage.viewmodels.RemoveCartViewModel
import com.app.shopin.homePage.views.Activity.CartPageActivity

class CartChildAdapater(
    var cartChildData: ArrayList<CartChildData>,
    var parentholder: CartParentAdapater.MyViewHolder,
    ) : RecyclerView.Adapter<CartChildAdapater.MyViewHolder>() {
    lateinit var binding: ItemCartChildBinding
    var overalltotal=0.0
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
                val pos = parentholder.adapterPosition
                CartParentAdapater.getInstance()?.runThread(pos)
                Log.e("data.id",data.inventory.toString())
                removeCart(data.inventory)
            }
            else
            {
                cartChildData.removeAt(position)
                notifyItemRemoved(position)
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

            if (it?.status == true && it.status_code==201) {

            } else {
            }
        }
        removeCartViewModel.removeCartResponse(
            ctx as CartPageActivity, id!!
        )
    }
}
