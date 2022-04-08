package com.app.shopin.UserAuth.view

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.views.Activity.HomeActivity
import com.app.shopin.utils.Constant
import com.app.shopin.utils.Preference

class SplashActivity : AppCompatActivity() {
    val arr = intArrayOf(0,1,2,0,1,2)

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


//        val n = arr.size
//
//        reversearr(arr, n)
//
//
//        for (i in 0 until arr.size) {
//            Log.e("afteraraaayyyy",arr[i].toString() + " ")
//        }
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

    fun sortArr(arr: IntArray, n: Int) {
        var i: Int
        var cnt0 = 0
        var cnt1 = 0
        var cnt2 = 0

        // Count the number of 0s, 1s and 2s in the array
        i = 0
        while (i < n) {
            when (arr[i]) {
                0 -> cnt0++
                1 -> cnt1++
                2 -> cnt2++
            }
            i++
        }

        // Update the array
        i = 0

        // Store all the 0s in the beginning
        while (cnt0 > 0) {
            arr[i++] = 0
            cnt0--
        }

        // Then all the 1s
        while (cnt1 > 0) {
            arr[i++] = 1
            cnt1--
        }

        // Finally all the 2s
        while (cnt2 > 0) {
            arr[i++] = 2
            cnt2--
        }

        // Print the sorted array
        for (i in 0 until n)
            Log.e("arr>>    ",arr[i].toString() + " ")    }


    fun reversearr(arr: IntArray, n: Int)
    {
        var start = 0
        var mid = 0
        var end: Int = arr.size - 1

        while (mid <= end) {
            Log.e("mid","start>> "+start+"  mid>>  "+mid.toString()+"  high>>  "+end.toString()+"  arr.get(mid)>>  "+arr.get(mid).toString())

            when (arr.get(mid)) {
                0 -> {
                    swap( start, mid)
                    start++
                    mid++
                }
                1 -> mid++
                2 -> {
                    swap( mid, end)
                    end--
                }
            }
        }
    }

    private fun swap( start: Int, end: Int) {

        val temp = arr[start]
        Log.e("temp",temp.toString())
        arr[start] = arr[end]
        Log.e("arr[start]",arr[start].toString())

        arr[end] = temp
        Log.e("arr[end]",arr[end].toString())



    }


}




