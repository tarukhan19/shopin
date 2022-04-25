package com.app.shopin.homePage.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.customview.BoldTextView
import com.app.shopin.databinding.ItemCartChildBinding
import com.app.shopin.homePage.models.CartChildData
import com.app.shopin.homePage.viewmodels.AddToCartViewModel
import com.app.shopin.homePage.viewmodels.RemoveCartViewModel
import com.app.shopin.homePage.views.Activity.CartPageActivity
import com.app.shopin.utils.Constant


class CartChildAdapater(
    var cartChildData: ArrayList<CartChildData>,
    var parentholder: CartParentAdapater.MyViewHolder,
    var progressbarLL: LinearLayout,
    var totalprice1TV: BoldTextView,
    var totalpriceTV: BoldTextView
) : RecyclerView.Adapter<CartChildAdapater.MyViewHolder>() {
    lateinit var binding: ItemCartChildBinding
    var ctx: Context? = null
    private lateinit var removeCartViewModel: RemoveCartViewModel
    private lateinit var addToCartViewModel: AddToCartViewModel

    var stockList = ArrayList<Int>()
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
        removeCartViewModel =
            ViewModelProvider(ctx as CartPageActivity).get(RemoveCartViewModel::class.java)
        addToCartViewModel =
            ViewModelProvider(ctx as CartPageActivity).get(AddToCartViewModel::class.java)
        holder.binding.prodnameTV.text = data.inventory_name
        holder.binding.prodamountTV.text = "$" + data.price + "/pc"


        try {

            Utils.setImage(
                holder.binding.productIV,
                Constant.IMAGE_BASE_URL + data.inventory_image,
                R.drawable.product
            )
        } catch (e: java.lang.Exception) {
        }


        holder.binding.quantityTV.setText(data.quantity)

            try {
                stockList.clear()
                Log.e("data.stock", data.stock.toString())
                for (i in 1..(data.stock)!!) {
                    stockList.add(i)
                }
                if (stockList.size != 0) {
                    var arrayAdapter = ArrayAdapter(ctx as CartPageActivity, R.layout.dropdown_menu, stockList)
                    // get reference to the autocomplete text view
                    // set adapter to the autocomplete tv to the arrayAdapter
                    holder.binding.quantityTV.setAdapter(arrayAdapter)
                    holder.binding.quantityTV.onItemClickListener =
                        OnItemClickListener { adapterView, view, pos, id ->

                            //update cart item with new value
                            val selectedValue: String = arrayAdapter.getItem(pos).toString()
                            val prodtotalamount = data.price * selectedValue.toInt()
                            cartChildData[position].quantity=selectedValue
                            cartChildData[position].total_amount=prodtotalamount

                            var overalltotal=0.0
                            for (i in 0..cartChildData.size-1)
                            {
                                overalltotal = cartChildData[i].total_amount+overalltotal
                            }

                            progressbarLL.visibility=View.VISIBLE

                            addToCart("1",selectedValue, prodtotalamount.toString(),data.price.toString(),
                                data.inventory!!, data.store.toString(),data.order_type,overalltotal
                            )
                        }
                }


            } catch (e: java.lang.Exception) {
                Log.e("stock excep",e.message.toString())
            }





        holder.binding.deleteLL.setOnClickListener {
            if (cartChildData.size == 1) {
                progressbarLL.visibility = View.VISIBLE
                val pos = parentholder.adapterPosition
                CartParentAdapater.getInstance()?.runThread(pos)
                removeCart(data.inventory)
            } else {
                progressbarLL.visibility = View.VISIBLE
                cartChildData.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, itemCount);
                removeCart(data.inventory)

            }
        }


    }

    private fun addToCart(
        is_update: String,
        quantity: String,
        totalamount: String,
        price: String,
        id: String,
        store: String,
        orderType: String?,
        overalltotal: Double
    )
    {
        var order_type=""
        if (orderType.equals("Delivery"))
        {
            order_type=Constant.DELIVERY
        }
        else
        {
            order_type=Constant.PICKUP

        }
        addToCartViewModel.addtocartviewmodel.removeObservers(ctx as CartPageActivity)
        addToCartViewModel.getObserveData().observe(ctx as CartPageActivity) {

            if (it?.status == true) {
              //  CartPageActivity.getInstance()?.runThread("cartdata","")
                totalpriceTV.text=overalltotal.toString()
                totalprice1TV.text=overalltotal.toString()

                progressbarLL.visibility = View.GONE

            } else {
                progressbarLL.visibility = View.GONE

            }
        }
        addToCartViewModel.addToCartResponse(
            ctx as CartPageActivity,
            order_type!!,
            price,
            quantity,
            is_update,
            id,
            "",
            totalamount,
            "",
            store
        )
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


    private fun removeCart(id: String?) {
        removeCartViewModel.removecartviewmodel.removeObservers(ctx as CartPageActivity)
        removeCartViewModel.getObserveData().observe(ctx as CartPageActivity) {

            if (it?.status == true) {
                progressbarLL.visibility = View.GONE

                Toast.makeText(ctx, "Removed", Toast.LENGTH_SHORT).show()
            } else {
                progressbarLL.visibility = View.GONE

            }
        }
        removeCartViewModel.removeCartResponse(
            ctx as CartPageActivity, id!!
        )
    }
}
