package com.app.shopin.homePage.views.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.shopin.R
import com.app.shopin.databinding.ActivityHomeBinding
import com.app.shopin.databinding.ActivityProductDetailBinding

class ProductDetailActivity : AppCompatActivity() {
    lateinit var binding:ActivityProductDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

    }
}