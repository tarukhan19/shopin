package com.customer.gogetme.Util

import android.app.Activity
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.app.shopin.R
import com.google.android.material.textfield.TextInputLayout
import com.app.shopin.Util.Utils

class MyTextWatcher(var view: EditText, var inputLayout: TextInputLayout, var activity: Activity) : TextWatcher {

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable) {
        when (view.id) {
            R.id.emailidET -> Utils.validateEditText("id", view, inputLayout, activity)
//            R.id.emailormobileET -> Utils.validateEditText("idmbl", view, inputLayout, activity)
//            R.id.passwordET-> Utils.validateEditText("pass",view,inputLayout,activity)
            R.id.nameET-> Utils.validateEditText("name",view,inputLayout,activity)
            R.id.mobilenoET-> Utils.validateEditText("phone",view,inputLayout,activity)
//
            R.id.businessnameET-> Utils.validateEditText("businessname",view,inputLayout,activity)
            R.id.businessaddressET-> Utils.validateEditText("businessaddress",view,inputLayout,activity)
//            R.id.cityET-> Utils.validateEditText("city",view,inputLayout,activity)
//            R.id.zipcodeET-> Utils.validateEditText("zipcode",view,inputLayout,activity)

            R.id.otpET1->
            {
                Utils.validateEditText("otp",view,inputLayout,activity)

            }
            R.id.otpET2->
            {
                Utils.validateEditText("otp",view,inputLayout,activity)
            }
            R.id.otpET3->
            {
                Utils.validateEditText("otp",view,inputLayout,activity)
            }
            R.id.otpET4->
            {
                Utils.validateEditText("otp",view,inputLayout,activity)
            }
            R.id.otpET5->
            {
                Utils.validateEditText("otp",view,inputLayout,activity)
            }
            R.id.otpET6->
            {
                Utils.validateEditText("otp",view,inputLayout,activity)
            }
//            R.id.mobilenoET-> Utils.validateEditText("phone",view,inputLayout,activity)
//            R.id.confpasswordET-> Utils.validateEditText("confirm_pass",view,inputLayout,activity)
//            R.id.oldpasswordET-> Utils.validateEditText("old_pass",view,inputLayout,activity)
//            R.id.titleET-> Utils.validateEditText("title",view,inputLayout,activity)
//            R.id.budgetET-> Utils.validateEditText("budget",view,inputLayout,activity)
//            R.id.descriptionET-> Utils.validateEditText("description",view,inputLayout,activity)
//


        }

    }

}