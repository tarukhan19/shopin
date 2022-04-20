package com.app.shopin.Util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Paint
import android.location.Address
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.app.shopin.R
import com.app.shopin.utils.Constant
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.textfield.TextInputLayout
import com.squareup.picasso.Picasso
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class Utils {
    companion object {

        var isGPSEnabled = false
        var isNetworkEnabled = false
        var status = false
        lateinit var address: List<Address>

        private lateinit var locationManager: LocationManager

        fun isValidEmail(mail: String): Boolean {

            if (TextUtils.isEmpty(mail)) {
                return false
            } else {
                val pattern: Pattern = Patterns.EMAIL_ADDRESS
                return pattern.matcher(mail).matches()
            }

        }

        suspend fun dateFormat(createdDate: String?):String {
            var formatted:String=""
            Log.e("createdDate",createdDate.toString())
            try {
                var date = createdDate
                val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val output = SimpleDateFormat("EEE, d MMM, yyyy")

                var d: Date? = null
                input.setTimeZone(TimeZone.getTimeZone("UTC"));
                d = input.parse(date)
                formatted = output.format(d)

            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return formatted
        }

        fun underline(textView: TextView) {
            textView.paintFlags = textView.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        }

        fun setImage(imageIV: ImageView, image: String, defultPic: Int) {
            try {
                Picasso.get().load(image).placeholder(defultPic).into(imageIV)
            } catch (e: Exception) {
                //Log.e("exception",e.localizedMessage)
            }
        }

        fun showToast(msg: String, ctx: Context) {
            Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show()
        }

        fun printLog(msg: String, tag: String) {
            Log.e(tag, msg)
        }

        fun getCalculatedDate(dateFormat: String?, days: Int): String? {
            val cal = Calendar.getInstance()
            val s = SimpleDateFormat(dateFormat)
            cal.add(Calendar.DAY_OF_YEAR, days)
            Log.e("dates", s.format(Date(cal.timeInMillis)).toString())
            return s.format(Date(cal.timeInMillis))
        }

        public fun validateEditText(
            msg: String,
            editText: EditText,
            inputlayout: TextInputLayout,
            activity: Activity
        ): Boolean {
            if (editText.text.toString().isEmpty()) {
                when (msg) {
                    "idmbl" -> inputlayout.error = activity.getString(R.string.error_empty_emailmbl)
                    "id" -> inputlayout.error = activity.getString(R.string.error_empty_email)
                    "pass" -> inputlayout.error = activity.getString(R.string.error_empty_password)
                    "name" -> inputlayout.error = activity.getString(R.string.error_empty_name)
                    "phone" -> inputlayout.error = activity.getString(R.string.error_empty_phone)
                    "otp" -> inputlayout.error = activity.getString(R.string.error_empty_otp)
                    "title" -> inputlayout.error = activity.getString(R.string.error_empty_title)
                    "budget" -> inputlayout.error = activity.getString(R.string.error_empty_budget)
                    "description" -> inputlayout.error =
                        activity.getString(R.string.error_empty_desc)
                    "address" -> inputlayout.error =
                        activity.getString(R.string.error_empty_address)
                    "city" -> inputlayout.error = activity.getString(R.string.error_empty_city)
                    "state" -> inputlayout.error = activity.getString(R.string.error_empty_state)
                    "zipcode" -> inputlayout.error =
                        activity.getString(R.string.error_empty_zipcode)
                    "old_pass" -> inputlayout.error =
                        activity.getString(R.string.error_empty_old_password)
                    "new_pass" -> inputlayout.error =
                        activity.getString(R.string.error_empty_new_password)
                    "confirm_pass" -> inputlayout.error =
                        activity.getString(R.string.error_empty_password)
                    "businessname" -> inputlayout.error =
                        activity.getString(R.string.error_empty_businessname)
                    "businessaddress" -> inputlayout.error =
                        activity.getString(R.string.error_empty_businessaddress)

                }
                requestFocus(editText, activity)
                return false

            } else if (!editText.text.toString().isEmpty() && msg.equals("phone")) {
                if ((editText.text.toString().length < 10)) {
                    inputlayout.error = activity.getString(R.string.error_empty_phone)
                    requestFocus(editText, activity)
                    return false
                } else {
                    inputlayout.isErrorEnabled = false
                    return true
                }

            } else if (!editText.text.toString().isEmpty() && msg.equals("id")) {
                if (!isValidEmail(editText.text.toString())) {
                    inputlayout.error = activity.getString(R.string.error_empty_email)
                    requestFocus(editText, activity)
                    return false
                } else {
                    inputlayout.isErrorEnabled = false
                    return true
                }

            } else {
                inputlayout.isErrorEnabled = false
                return true
            }

        }


        public fun validatePassMismatchEditText(
            msg: String,
            editText: EditText,
            inputlayout: TextInputLayout,
            activity: Activity
        ): Boolean {
            if (!editText.text.toString().isEmpty()) {
                when (msg) {


                    "mismatch" -> inputlayout.error =
                        activity.getString(R.string.text_password_mismatch)

                }
                requestFocus(editText, activity)
                return false

            }


            inputlayout.isErrorEnabled = false
            return true
        }


        private fun requestFocus(editText: View, activity: Activity) {
            try {
                if (editText.requestFocus()) {
                    activity.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
                }
            } catch (e: Exception) {
            }


        }

        fun hideKeyBord(ctx: Activity) {
            try {
                val methodManager =
                    ctx.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                methodManager.hideSoftInputFromWindow(ctx.currentFocus!!.windowToken, 0)

            } catch (e: Exception) {
            }

        }

        fun isNetworkConnected(context: Context?): Boolean {
            if (context != null) {
                val connectivityManager =
                    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val networkInfo = connectivityManager.activeNetworkInfo
                if (networkInfo != null) {
                    if (networkInfo.type == ConnectivityManager.TYPE_WIFI || networkInfo.type == ConnectivityManager.TYPE_MOBILE) {
                        return true
                    }
                }
            }
            return false

        }


        fun checkPermission(ctx: Context): Boolean {
            return (((checkSelfPermission(ctx, Manifest.permission.READ_EXTERNAL_STORAGE)) +
                    (checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION)) +
                    (checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION)) +
                    (checkSelfPermission(ctx, Manifest.permission.CALL_PHONE)) +
                    (checkSelfPermission(ctx, Manifest.permission.CAMERA)))
                    == PackageManager.PERMISSION_GRANTED)
        }


        // current location

        fun checkLocationOn(activity: Activity): Boolean {
            locationManager =
                activity.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder()
            builder.addLocationRequest(
                LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(10 * 1000)
                    .setFastestInterval(1 * 1000)
            )
            builder.setAlwaysShow(true)
            val mLocationSettingsRequest: LocationSettingsRequest = builder.build()
            val settingClient: SettingsClient = LocationServices.getSettingsClient(activity)

            settingClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener {

                    isGPSEnabled =
                        locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

                    isNetworkEnabled =
                        locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)


                    if (!isGPSEnabled && !isNetworkEnabled) {

                        // checkLocationOn(activity)
                        status = false

                    } else {

                        if (isNetworkEnabled) {

                            status = true

                        }

                    }


                }
                .addOnCanceledListener {


                }
                .addOnFailureListener { e: java.lang.Exception ->
                    val statusCode = (e as ApiException).statusCode
                    when (statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            try {
                                var rae = e as ResolvableApiException
                                activity.startIntentSenderForResult(
                                    e.resolution.intentSender,
                                    Constant.REQUEST_CHECK_SETTINGS,
                                    null,
                                    0,
                                    0,
                                    0,
                                    null
                                )
                            } catch (e: IntentSender.SendIntentException) {
                            }
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {

                        }

                    }
                }
            return status

        }


        fun checkPermissions(ctx: Context, permission: String): Boolean {

            val permission = ContextCompat.checkSelfPermission(ctx, permission)

            return permission == PackageManager.PERMISSION_GRANTED


        }

        fun enableLocationSettings(activity: Activity) {
            val locationRequest = LocationRequest.create()
                .setInterval(1000)
                .setFastestInterval(5000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)
            LocationServices
                .getSettingsClient(activity)
                .checkLocationSettings(builder.build())
                .addOnSuccessListener(
                    activity
                ) { response: LocationSettingsResponse? -> }
                .addOnFailureListener(
                    activity
                ) { ex: java.lang.Exception? ->
                    if (ex is ResolvableApiException) {
                        // Location settings are NOT satisfied,  but this can be fixed  by showing the user a dialog.
                        try {

                            ex.startResolutionForResult(
                                activity,
                                Constant.REQUEST_CHECK_SETTINGS
                            )
                            //       resultLauncher.launch(intent)

                        } catch (sendEx: IntentSender.SendIntentException) {
                            // Ignore the error.
                            // Log.e("sendEx",sendEx.localizedMessage)

                        }
                    }
                }
        }


        fun isLocationEnabled(ctx: Context): Boolean {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {

                val lm = ctx.getSystemService(Context.LOCATION_SERVICE) as LocationManager
                return lm.isLocationEnabled
            } else {

                // This is Deprecated in API 28
                val mode: Int = Settings.Secure.getInt(
                    ctx.getContentResolver(), Settings.Secure.LOCATION_MODE,
                    Settings.Secure.LOCATION_MODE_OFF
                );
                return (mode != Settings.Secure.LOCATION_MODE_OFF);

            }
        }

    }


}