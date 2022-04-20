package com.app.shopin.homePage.views.Activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.app.shopin.R
import com.app.shopin.UserAuth.view.EmailRegisterActivity
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ActivityStoreDetailBinding
import com.app.shopin.homePage.models.DeliveryAddressData
import com.app.shopin.homePage.models.DeliveryAddressListResponse
import com.app.shopin.homePage.viewmodels.ProductDetailViewModel
import com.app.shopin.homePage.viewmodels.StoreDetailViewModel
import com.app.shopin.homePage.views.Fragment.StoreDetailFeatureFragment
import com.app.shopin.utils.Constant
import com.app.shopin.utils.Preference
import com.app.shopin.utils.TimeDateConversion
import kotlinx.android.synthetic.main.activity_delivery_address_list.*
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_store_detail.*

class StoreDetailActivity : AppCompatActivity() , View.OnClickListener{
    lateinit var binding:ActivityStoreDetailBinding
    var selectedtab:String="feature"
    var id:String=""
    lateinit var storeDetailViewModel: StoreDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_store_detail)
        initialize()
    }

    fun initialize()
    {
        storeDetailViewModel = ViewModelProvider(this).get(StoreDetailViewModel::class.java)
        id= intent.getStringExtra("id")!!
        featuredLL.setOnClickListener(this)
        categoryLL.setOnClickListener(this)
        foodLL.setOnClickListener(this)
        backIV.setOnClickListener(this)
        cartLL.setOnClickListener(this)
        addFragment(StoreDetailFeatureFragment.newInstance())
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.backIV -> {
                finish()
            }

            R.id.cartLL -> {
                val in7 = Intent(this, CartPageActivity::class.java)
                startActivity(in7)
                overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
            }
        }
    }

    private fun addFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.add(R.id.frameLL,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }

    fun loadStore()
    {
        storeDetailViewModel.getStoreDetailObserver().observe(this) {
            if (it?.status == true && it.status_code == 200) {
                val data= it.data.store
                Log.e("storedata",data.toString())
                storenameTV.text=data.name
                addressTV.text=data.address
                try {
                    val storeTime = it.data.store_timmings
                    val openTime = TimeDateConversion.convertTime(storeTime.opening_time)
                    val closetime = TimeDateConversion.convertTime(storeTime.closing_time)
                    val businesstype=data.business_type
                    if (businesstype)
                    {
                        val time=openTime + " - " + closetime +" Open"
                        binding.timeTV.text =time

                    }
                    else
                    {
                        val time=openTime + " - " + closetime +" Close"
                        binding.timeTV.text =time

                    }
                } catch (e: Exception) {
                    Log.e("exce", e.message.toString())
                }
            } else {
                progressbarLL.visibility = View.GONE
                Utils.showToast("Something Went wrong", this)
            }
        }
        storeDetailViewModel.getStoreDetail(this,id)
    }

    override fun onResume() {
        super.onResume()
        loadStore()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}