package com.app.shopin.services

import android.Manifest
import android.annotation.SuppressLint
import android.app.IntentService
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.app.shopin.Util.Utils
import com.app.shopin.utils.Constant
import com.app.shopin.utils.Preference


class GetLocationService : IntentService("MyService"), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener {

    private val TAG = GetLocationService::class.java.getSimpleName()
    lateinit var mLocationClient: GoogleApiClient
    var mLocationRequest = LocationRequest()
    var updateInterval: Long = 15000
    var fastesInterval: Long = 5000

    companion object {
        val ACTION_LOCATION_BROADCAST = GetLocationService::class.java.getName() + "LocationBroadcast"
        val EXTRA_LATITUDE = "extra_latitude"
        val EXTRA_LONGITUDE = "extra_longitude"
    }

    override fun onHandleIntent(intent: Intent?) {
        mLocationClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

        mLocationRequest.interval = updateInterval
        mLocationRequest.fastestInterval = fastesInterval
        val priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.priority = priority
        mLocationClient.connect()
        stopSelf()
        //return START_STICKY

    }
   /* override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        mLocationClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()

        mLocationRequest.interval = updateInterval
        mLocationRequest.fastestInterval = fastesInterval
        val priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.priority = priority
        mLocationClient.connect()
        stopSelf()
        return START_STICKY
    }*/

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.d(TAG, "ondestroy")
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
    override fun onConnected(p0: Bundle?) {

        if (Utils.checkPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

            LocationServices.FusedLocationApi.requestLocationUpdates(mLocationClient, mLocationRequest, this)
            Log.d(TAG, "Connected to Google API")

        }


    }

    override fun onConnectionSuspended(p0: Int) {
        Log.d(TAG, "Connection suspended")

    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.d(TAG, "Failed to connect to Google API")

    }

    override fun onLocationChanged(location: Location?) {
        Log.d(TAG, "Location changed")
        if (location != null) {
            val add = Utils.getAddress(this,location.latitude,location.longitude)
            Log.i(TAG, "currentLoc: " + location.latitude + ", " + location.longitude + ", " + add)

            //Send result to activities

            sendMessageToUI(location.latitude.toString(), location.longitude.toString(),add)
            LocationServices.FusedLocationApi.removeLocationUpdates(mLocationClient, this)

        }
    }

    private fun sendMessageToUI(lat: String, lng: String, add: String?) {

        val intent = Intent(ACTION_LOCATION_BROADCAST)
        intent.putExtra(EXTRA_LATITUDE, lat)
        intent.putExtra(EXTRA_LONGITUDE, lng)

        Preference.getInstance(this)?.setString(Constant.LOCATION_LAT, lat)
        Preference.getInstance(this)?.setString(Constant.LOCATION_LONG, lng)
        if (add != null) {
            Preference.getInstance(this)?.setString(Constant.LOCALITY, add)
        }


        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)

    }



}