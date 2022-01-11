package com.app.shopin.UserAuth.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.shopin.HomePage.view.HomeActivity
import com.app.shopin.R
import com.app.shopin.utils.Constant
import com.app.shopin.utils.Preference

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.

        Handler(Looper.getMainLooper()).postDelayed({
            if (Preference.getInstance(this)?.getBoolean(Constant.IS_LOGIN) == true)
            {
                val intent = Intent(this@SplashActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }else{
                val intent = Intent(this@SplashActivity, WelcomeToShop::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }, 5000)


    }

}