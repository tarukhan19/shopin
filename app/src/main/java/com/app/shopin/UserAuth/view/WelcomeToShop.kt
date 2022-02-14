package com.app.shopin.UserAuth.view

import android.Manifest
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.utils.Constant
import kotlinx.android.synthetic.main.activity_welcome_to_shop.*

class WelcomeToShop : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_to_shop)

        nextBTN.setOnClickListener {

            if (Utils.checkPermission(this)) {
                if (Utils.isLocationEnabled(this)) {
                    openActivity()
                } else {
                    Utils.enableLocationSettings(this)
                }
            }
            else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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

    fun  openActivity()
    {
        val in7 = Intent(this, EmailRegisterActivity::class.java)
        in7.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(in7)
        overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
    }
}