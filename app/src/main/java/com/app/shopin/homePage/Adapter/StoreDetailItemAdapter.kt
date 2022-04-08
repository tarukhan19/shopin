package com.app.shopin.homePage.Adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ItemProductBinding
import com.app.shopin.homePage.models.StoreInventoryData
import com.app.shopin.homePage.viewmodels.AddToCartViewModel
import com.app.shopin.homePage.viewmodels.RemoveCartViewModel
import com.app.shopin.utils.Constant
import com.app.shopin.utils.Preference
import kotlinx.android.synthetic.main.activity_get_my_store_listed.*
import java.lang.Exception

class StoreDetailItemAdapter(
    var ctx: Context,
    var storeCategoryData: ArrayList<StoreInventoryData>,
    var progressbarLL: LinearLayout
) : RecyclerView.Adapter<StoreDetailItemAdapter.MyViewHolder>() {
    lateinit var binding: ItemProductBinding
    var activity: Activity? = null
    var stock_quantity=0
    var cart_quantity=0
    private lateinit var addToCartViewModel: AddToCartViewModel
    private lateinit var removeCartViewModel: RemoveCartViewModel

    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        ctx = parent.context
        activity = ctx as Activity
        binding = DataBindingUtil.inflate(LayoutInflater.from(ctx), R.layout.item_product, parent, false)
        addToCartViewModel = ViewModelProvider(ctx as FragmentActivity).get(AddToCartViewModel::class.java)
        removeCartViewModel = ViewModelProvider(ctx as FragmentActivity).get(RemoveCartViewModel::class.java)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int)
    {
        val storeInventoryData = storeCategoryData[position]
        holder.binding.prodnameTV.setText(storeInventoryData.name)
        holder.binding.prodpriceTV.setText("$"+storeInventoryData.price)
        cart_quantity= storeInventoryData.cart_quatity!!
        if (cart_quantity==0)
        {
            holder.binding.plusIV.visibility=View.VISIBLE
            holder.binding.expandLL.visibility=View.GONE
        }
        else
        {
            holder.binding.plusIV.visibility=View.GONE
            holder.binding.expandLL.visibility=View.VISIBLE
            holder.binding.prodquantityTV.text= cart_quantity.toString()
        }

        holder.binding.plusIV.setOnClickListener{
            progressbarLL.visibility=View.VISIBLE
            stock_quantity= storeInventoryData.cart_quatity!!
            Utils.printLog(storeInventoryData.name+" ","plusstoreInventoryData")

            cart_quantity=0
                holder.binding.plusIV.visibility=View.GONE
                holder.binding.expandLL.visibility=View.VISIBLE
                cart_quantity=cart_quantity+1
                holder.binding.prodquantityTV.setText(cart_quantity.toString())
                val totalamount= storeInventoryData.price!!.toDouble()*cart_quantity
            try {
                addToCart(storeInventoryData,"0",cart_quantity.toString(), totalamount.toString())

            }
            catch (e: Exception)
            {

            }
        }
        holder.binding.expplusIV.setOnClickListener {
            stock_quantity= storeInventoryData.stock_quantity!!
            Utils.printLog(storeInventoryData.name+" ","explusstoreInventoryData")

            if (storeInventoryData.cart_quatity!=0)
            {
                cart_quantity= storeInventoryData.cart_quatity!!

            }

            if (cart_quantity < stock_quantity)
            {
                progressbarLL.visibility=View.VISIBLE

                cart_quantity=cart_quantity+1
                holder.binding.prodquantityTV.setText(cart_quantity.toString())
                val totalamount= storeInventoryData.price!!.toDouble()*cart_quantity
                storeInventoryData.cart_quatity=cart_quantity
                try {
                    addToCart(storeInventoryData,"1",cart_quantity.toString(), totalamount.toString())

                }
                catch (e: Exception)
                {

                }
            }
            else
            {
                Utils.showToast("Out of stock",ctx)

            }


        }
        holder.binding.expminusIV.setOnClickListener {
            Utils.printLog(storeInventoryData.name+" ","exminusstoreInventoryData")
            progressbarLL.visibility=View.VISIBLE

            cart_quantity=holder.binding.prodquantityTV.text.toString().toInt()

            if (cart_quantity==1)
            {
                holder.binding.plusIV.visibility=View.VISIBLE
                holder.binding.expandLL.visibility=View.GONE
                try {
                    removeCart(storeInventoryData.id)

                }
                catch (e:Exception)
                {}

            }
            else
            {
                    cart_quantity=cart_quantity-1
                    holder.binding.prodquantityTV.setText(cart_quantity.toString())
                    val totalamount= storeInventoryData.price!!.toDouble()*cart_quantity
                try {
                    addToCart(storeInventoryData,"1",cart_quantity.toString(), totalamount.toString())

                }
                catch (e: Exception)
                {

                }

            }

        }
    }

    override fun getItemCount(): Int {
        return storeCategoryData.size
    }


    class MyViewHolder(itemView: ItemProductBinding) :
        RecyclerView.ViewHolder(itemView.root) {
        val binding: ItemProductBinding

        init {
            binding = itemView
        }
    }


    private fun addToCart(storeInventoryData: StoreInventoryData,is_update:String,quantity:String,totalamount:String)
    {

        val ordertype=Preference.getInstance(ctx)?.getString(Constant.INSTORE_OR_DELIVERY)

        addToCartViewModel.addtocartviewmodel.removeObservers(ctx as FragmentActivity)
        addToCartViewModel.getObserveData().observe(ctx as FragmentActivity) {

            if (it?.status == true && it.status_code==201) {
                progressbarLL.visibility=View.GONE

            } else {
                progressbarLL.visibility=View.GONE

            }
        }
        addToCartViewModel.addToCartResponse(
            ctx, ordertype!!,storeInventoryData.price!!,quantity, is_update, storeInventoryData.id!!,
            "",totalamount,"", storeInventoryData.store!!
        )
    }


    private fun removeCart(id: String?)
    {
        removeCartViewModel.removecartviewmodel.removeObservers(ctx as FragmentActivity)
        removeCartViewModel.getObserveData().observe(ctx as FragmentActivity) {

            if (it?.status == true && it.status_code==201) {
                progressbarLL.visibility=View.GONE

            } else {
                progressbarLL.visibility=View.GONE

            }
        }
        removeCartViewModel.removeCartResponse(
            ctx, id!!
        )
    }
}
