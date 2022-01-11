package com.app.shopin.UserAuth.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.shopin.R
import kotlinx.android.synthetic.main.activity_welcome_to_shop.*

class WelcomeToShop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_to_shop)
        nextBTN.setOnClickListener {
            val in7 = Intent(this, EmailRegisterActivity::class.java)
            in7.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(in7)
            overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
        }
    }
}