package com.app.shopin.utils

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import androidx.core.app.ActivityCompat
import com.app.shopin.UserAuth.view.OtpActivity
import com.app.shopin.UserAuth.viewmodel.LoadProfileViewModel
import com.app.shopin.Util.Utils
import com.app.shopin.homePage.views.Activity.HomeActivity
import kotlinx.android.synthetic.main.activity_otp.*
import java.lang.Exception
import java.util.*

class LocationMethods {
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
        lateinit var locationManager: LocationManager
        var hasGps: Boolean = false
        var hasNetwork: Boolean = false
        lateinit var locationNetwork: Location
        private var lat: Double =0.0
        private var lng: Double =0.0

        var loc_listener = object : LocationListener {
            private val TAG = "xoxoxo.LocationListener"
            override fun onLocationChanged(p0: Location) {

                // locationManager.requestLocationUpdates("gps", 0 ,0, this);
                try {
                    locationNetwork = p0
                    lat = locationNetwork.latitude
                    lng = locationNetwork.longitude

                    Preference.getInstance(context)?.setString(Constant.CURRENT_LOCATION_LAT, lat.toString())
                    Preference.getInstance(context)?.setString(Constant.CURRENT_LOCATION_LONG, lng.toString())


                } catch (e: Exception) {
                    // //Log.e("exceptionnn", e.message.toString())
                }

            }

            override fun onProviderEnabled(p: String) {
                Log.i(TAG, "Provider enabled")
            }

            override fun onProviderDisabled(p: String) {
                Log.i(TAG, "Provider disabled")
            }

            override fun onStatusChanged(p: String, status: Int, extras: Bundle) {
                Log.i(TAG, "Status changed")
            }
        }

        ///get currrent lat long
         fun getLocation(ctx: Context): Address?
        {
            context=ctx
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var address: Address? =null

            hasNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (hasGps || hasNetwork) {

                if (hasNetwork) {

                    if (ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED) {

                    }
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        1000,
                        1000F,
                        loc_listener

                    )
                     address =getCompleteAddressString(lat, lng,ctx)

                    val localNetworkLocation =
                        locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    if (localNetworkLocation != null)
                        locationNetwork = localNetworkLocation
                }



                if ( locationNetwork != null)
                {

                    lat=locationNetwork.latitude
                    lng=locationNetwork.longitude

                    Preference.getInstance(context)?.setString(Constant.CURRENT_LOCATION_LAT, lat.toString())
                    Preference.getInstance(context)?.setString(Constant.CURRENT_LOCATION_LONG, lng.toString())


                    address =getCompleteAddressString(lat, lng,ctx)


                }


            } else {
                context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
            }

            return address
        }


        fun stopListener()
        {
            locationManager.removeUpdates(loc_listener);

        }

         fun getCompleteAddressString(
            LATITUDE: Double,
            LONGITUDE: Double,
            ctx: Context
        ): Address? {
            context = ctx
              var returnedAddress: Address? =null
            val geocoder = Geocoder(context, Locale.getDefault())
            try {
                val addresses: List<Address>? = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1)
                if (addresses != null) {
                     returnedAddress = addresses[0]
                }

            }
            catch (e:Exception)
            {

            }
            return returnedAddress

        }




    }
}