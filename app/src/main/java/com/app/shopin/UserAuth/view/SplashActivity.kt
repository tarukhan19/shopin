package com.app.shopin.UserAuth.view

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.annotation.RequiresApi
import com.app.shopin.homePage.views.Activity.HomeActivity
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.utils.Constant
import com.app.shopin.utils.Preference

class SplashActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        if (Utils.checkPermission(this)) {
            if (Utils.isLocationEnabled(this)) {
                openActivity()
            } else {
                Utils.enableLocationSettings(this)
            }
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.CALL_PHONE,
                    Manifest.permission.CAMERA
                ),
                Constant.PERMISSIONS_REQUEST
            )
        }



    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {

            Constant.PERMISSIONS_REQUEST -> {
                if (Utils.isLocationEnabled(this)) {
                    openActivity()
                } else {
                    Utils.checkLocationOn(this)
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (Constant.REQUEST_CHECK_SETTINGS == requestCode) {
            if (RESULT_OK == resultCode) {
                openActivity()
            } else {
                Utils.checkLocationOn(this)
            }
        }
    }


    fun openActivity()
    {
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
    }, 3000)
    }

}