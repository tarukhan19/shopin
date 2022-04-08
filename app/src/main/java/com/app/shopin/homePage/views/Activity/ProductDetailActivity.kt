package com.app.shopin.homePage.views.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ActivityProductDetailBinding
import com.app.shopin.homePage.Adapter.FeaturedItemAdapter
import com.app.shopin.homePage.models.*
import com.app.shopin.homePage.viewmodels.AddToCartViewModel
import com.app.shopin.homePage.viewmodels.ProductDetailViewModel
import com.app.shopin.homePage.viewmodels.RemoveCartViewModel
import com.app.shopin.utils.Constant
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.toolbar_search.*
import kotlinx.android.synthetic.main.toolbar_search.view.*
import java.lang.Exception

class ProductDetailActivity : AppCompatActivity(),View.OnClickListener {
    lateinit var binding:ActivityProductDetailBinding
    lateinit var productDetailViewModel: ProductDetailViewModel
    var stock_quantity=0
    var cart_quantity=0
    var price=0.0
    var prodid=""
    var storeid=""

    private lateinit var addToCartViewModel: AddToCartViewModel
    private lateinit var removeCartViewModel: RemoveCartViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)
        initialize()
    }

    private fun initialize()
    {
        prodid= intent.getStringExtra("id")!!
        productDetailViewModel = ViewModelProvider(this).get(ProductDetailViewModel::class.java)
        addToCartViewModel = ViewModelProvider(this).get(AddToCartViewModel::class.java)
        removeCartViewModel = ViewModelProvider(this).get(RemoveCartViewModel::class.java)

        val featuredItemListData = ArrayList<FeaturedItemListData>()
        featuredItemListData.add(FeaturedItemListData("Dove shampoo hair fall rescue shampoo","10ml","$50/pc", R.drawable.dove))
        featuredItemListData.add(FeaturedItemListData("Pears body wash rescue shampoo","100ml","$500/pc", R.drawable.dove))
        featuredItemListData.add(FeaturedItemListData("Mildy shampoo","60ml","$340/pc", R.drawable.dove))
        featuredItemListData.add(FeaturedItemListData("Dettol handwash","100ml","$509/pc", R.drawable.dove))
        featuredItemListData.add(FeaturedItemListData("Clean & Clear facewash","90ml","$80/pc", R.drawable.dove))

        val featuredItemAdapter = FeaturedItemAdapter(featuredItemListData)

        featureditemRV.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        featureditemRV.adapter = featuredItemAdapter

        relateditemRV.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        relateditemRV.adapter = featuredItemAdapter

        toolbar.back_LL.visibility=View.VISIBLE
        toolbar.titleTV.setText("Product Detail")
        addtocartBTN.setOnClickListener(this)
        expplussIV.setOnClickListener(this)
        expminussIV.setOnClickListener(this)
        addtocartLL.setOnClickListener(this)
        toolbar.back_LL.setOnClickListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getProductDetail() {
        productDetailViewModel.getproductDetailObserveData().observe(this, Observer<ProductDetailResponse> {
            if (it != null) {
                if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                    binding.progressbarLL.visibility=View.GONE

                    cart_quantity= it.data.cart_quantity

                    val inventeryItem = it.data.inventry_item
                    setData(inventeryItem)

                }
            } else {
                binding.progressbarLL.visibility=View.GONE

                Toast.makeText(this, "Something wrong with DeliveryAddress Api", Toast.LENGTH_SHORT).show()
            }
        })
        productDetailViewModel.getProdDetailResp(this,prodid)
    }

    private fun setData(inventeryItem: StoreInventoryData) {
        stock_quantity= inventeryItem.stock_quantity!!
        price= inventeryItem.price!!.toDouble()
        storeid = inventeryItem.store!!

        if (cart_quantity==0)
        {
            addtocartBTN.visibility=View.VISIBLE
            expanddLL.visibility=View.INVISIBLE
        }
        else
        {
            addtocartBTN.visibility=View.INVISIBLE
            expanddLL.visibility=View.VISIBLE
            productquantityTV.text= cart_quantity.toString()
        }


        productnameTV.setText(inventeryItem.name)
        productidTV.setText("Product ID -"+inventeryItem.id)
        priceTV.setText(inventeryItem.price.toString()+inventeryItem.size_unit+"/pc")
        sizeTV.setText(inventeryItem.size+inventeryItem.size_unit)
        descriptionTV.setText(inventeryItem.description+getString(R.string.loremipsum))
        returnpolicyTV.setText(inventeryItem.return_policy+getString(R.string.loremipsum))
    }

    override fun onResume() {
        super.onResume()
        binding.progressbarLL.visibility=View.VISIBLE
        getProductDetail()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.addtocartBTN -> {
                binding.progressbarLL.visibility=View.VISIBLE

                expanddLL.visibility=View.VISIBLE
                addtocartBTN.visibility=View.INVISIBLE
                cart_quantity=0
                cart_quantity=cart_quantity+1
                productquantityTV.setText(cart_quantity.toString())
                val totalamount= price*cart_quantity
                try {
                    addToCart("0",cart_quantity.toString(), totalamount.toString())

                }catch (e:Exception)
                {}
            }
            R.id.expminussIV -> {
                binding.progressbarLL.visibility=View.VISIBLE

                cart_quantity=productquantityTV.text.toString().toInt()

                if (cart_quantity==1)
                {
                    addtocartBTN.visibility=View.VISIBLE
                    expanddLL.visibility=View.INVISIBLE
                    try {
                        removeCart()

                    }
                    catch (e:Exception)
                    {

                    }

                }
                else
                {
                    binding.progressbarLL.visibility=View.VISIBLE

                    cart_quantity=cart_quantity-1
                    productquantityTV.setText(cart_quantity.toString())
                    val totalamount= price*cart_quantity
                    try {
                        addToCart("1",cart_quantity.toString(), totalamount.toString())

                    }catch (e:Exception)
                    {}

                }

            }

            R.id.expplussIV -> {

                if (cart_quantity < stock_quantity)
                {
                    cart_quantity=cart_quantity+1
                    productquantityTV.setText(cart_quantity.toString())
                    val totalamount= price*cart_quantity
                    try {
                        addToCart("1",cart_quantity.toString(), totalamount.toString())

                    }catch (e:Exception)
                    {}
                }
                else
                {
                    Utils.showToast("Out of stock",this)

                }

            }

            R.id.addtocartLL -> {
                val in7 = Intent(this, CartPageActivity::class.java)
                startActivity(in7)
                overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
            }

            R.id.back_LL ->
            {
                finish()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun addToCart(is_update:String,quantity:String,totalamount:String)
    {
        addToCartViewModel.addtocartviewmodel.removeObservers(this)
        addToCartViewModel.getObserveData().observe(this) {

            if (it?.status == true && it.status_code==201) {
                binding.progressbarLL.visibility=View.GONE

            }
            else {
                binding.progressbarLL.visibility=View.GONE

            }
        }
        addToCartViewModel.addToCartResponse(
            this, Constant.DELIVERY,price.toString(),quantity, is_update, prodid,
            "",totalamount,"", storeid)
    }

    private fun removeCart()
    {

        removeCartViewModel.removecartviewmodel.removeObservers(this)
        removeCartViewModel.getObserveData().observe(this) {

            if (it?.status == true && it.status_code==201) {
                binding.progressbarLL.visibility=View.GONE

            } else {
                binding.progressbarLL.visibility=View.GONE

            }
        }
        removeCartViewModel.removeCartResponse(
            this, prodid
        )
    }
}