package com.app.shopin.HomePage.view.Activity

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.app.shopin.HomePage.view.Fragment.HomeFragment
import com.app.shopin.HomePage.view.Fragment.MoreFragment
import com.app.shopin.HomePage.view.Fragment.SearchFragment
import com.app.shopin.HomePage.view.Fragment.StoreFragment
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ActivityHomeBinding
import com.app.shopin.services.GetLocationService
import com.app.shopin.utils.Constant
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private lateinit var receiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(
            this,
            R.layout.activity_home
        )

        addFragment(HomeFragment.newInstance())
        bottomNavigation.show(0)
        bottomNavigation.add(MeowBottomNavigation.Model(0,R.drawable.home_inactive))
        bottomNavigation.add(MeowBottomNavigation.Model(1,R.drawable.store_inactive))
        bottomNavigation.add(MeowBottomNavigation.Model(2,R.drawable.search_inactive))
        bottomNavigation.add(MeowBottomNavigation.Model(3,R.drawable.profile_inactive))


        if (Utils.checkPermission(this)) {
            if (!Utils.isLocationEnabled(this)) {
                Utils.enableLocationSettings(this)
            }
            else {
                getLocation(true)
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


        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                0 -> {
                    replaceFragment(HomeFragment.newInstance())
                }
                1 -> {
                    replaceFragment(StoreFragment.newInstance())
                }
                2 -> {
                    replaceFragment(SearchFragment.newInstance())
                }
                3 -> {
                    replaceFragment(MoreFragment.newInstance())
                }

                else -> {
                    replaceFragment(HomeFragment.newInstance())
                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }

    private fun addFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.add(R.id.fragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
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
                    getLocation(true)
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
                getLocation(true)
            } else {
                Utils.checkLocationOn(this)
            }
        }
    }



    private fun getLocation(locChange: Boolean) {

        val intent = Intent(this, GetLocationService::class.java)
        startService(intent)
        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val lat = intent.getStringExtra(GetLocationService.EXTRA_LATITUDE)
                val lng = intent.getStringExtra(GetLocationService.EXTRA_LONGITUDE)
                val address =
                    Utils.getAddress(context, lat!!.toDouble(), lng!!.toDouble())

                Log.e(
                    "tag",
                    "Geting Current Loc, $lat, $lng , " + Utils.getAddress(
                        context,
                        lat.toDouble(),
                        lng.toDouble()
                    )
                )
               // checkInternetConnectivity(locChange)
                stopService(Intent(this@HomeActivity, GetLocationService::class.java))
                LocalBroadcastManager.getInstance(this@HomeActivity).unregisterReceiver(receiver)
            }
        }

        val lbm = LocalBroadcastManager.getInstance(this)
        val filter = IntentFilter()
        filter.addAction(GetLocationService.ACTION_LOCATION_BROADCAST)
        lbm.registerReceiver(receiver, filter)


    }

}