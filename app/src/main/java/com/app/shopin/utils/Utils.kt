package com.app.shopin.Util

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.app.shopin.R
import com.google.android.material.textfield.TextInputLayout
import java.util.regex.Pattern

class Utils {
    companion object {
        fun isValidEmail(mail: String): Boolean {

            if (TextUtils.isEmpty(mail)) {
                return false
            } else {
                val pattern: Pattern = Patterns.EMAIL_ADDRESS
                return pattern.matcher(mail).matches()
            }

        }

        fun showToast(msg:String, ctx:Context) {
        Toast.makeText(ctx,msg,Toast.LENGTH_LONG).show()
        }

        fun printLog(msg:String,tag:String) {
            Log.e(tag,msg)
        }


        public fun validateEditText(
            msg: String,
            editText: EditText,
            inputlayout: TextInputLayout,
            activity: Activity
        ): Boolean {
            if (editText.text.toString().isEmpty())
            {
                when (msg) {
                    "idmbl" -> inputlayout.error = activity.getString(R.string.error_empty_emailmbl)
                    "id" -> inputlayout.error = activity.getString(R.string.error_empty_email)
                    "pass" -> inputlayout.error = activity.getString(R.string.error_empty_password)
                    "name" -> inputlayout.error = activity.getString(R.string.error_empty_name)
                    "phone" -> inputlayout.error = activity.getString(R.string.error_empty_phone)
                    "otp" -> inputlayout.error = activity.getString(R.string.error_empty_otp)
                    "title" -> inputlayout.error = activity.getString(R.string.error_empty_title)
                    "budget" -> inputlayout.error = activity.getString(R.string.error_empty_budget)
                    "description" -> inputlayout.error = activity.getString(R.string.error_empty_desc)
                    "address" -> inputlayout.error = activity.getString(R.string.error_empty_address)
                    "city" -> inputlayout.error = activity.getString(R.string.error_empty_city)
                    "state" -> inputlayout.error = activity.getString(R.string.error_empty_state)
                    "zipcode" -> inputlayout.error = activity.getString(R.string.error_empty_zipcode)
                    "old_pass" -> inputlayout.error =
                        activity.getString(R.string.error_empty_old_password)
                    "new_pass" -> inputlayout.error =
                        activity.getString(R.string.error_empty_new_password)
                    "confirm_pass" -> inputlayout.error =
                        activity.getString(R.string.error_empty_password)
                }
                requestFocus(editText, activity)
                return false

            }
            else if (!editText.text.toString().isEmpty() && msg.equals("phone"))
            {
                if ((editText.text.toString().length<10))
                {
                    inputlayout.error = activity.getString(R.string.error_empty_phone)
                    requestFocus(editText, activity)
                    return false
                }
                else
                {
                    inputlayout.isErrorEnabled = false
                    return true
                }

            }

            else if (!editText.text.toString().isEmpty() && msg.equals("id"))
            {
                if (!isValidEmail(editText.text.toString()))
                {
                    inputlayout.error = activity.getString(R.string.error_empty_email)
                    requestFocus(editText, activity)
                    return false
                }
                else
                {
                    inputlayout.isErrorEnabled = false
                    return true
                }

            }
            else

            {
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
    }
}