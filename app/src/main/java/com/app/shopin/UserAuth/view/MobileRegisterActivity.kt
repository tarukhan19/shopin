package com.app.shopin.UserAuth.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.shopin.HomePage.view.HomeActivity
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ActivityMobileRegisterBinding
import com.app.shopin.utils.Constant
import com.app.shopin.utils.Preference
import com.customer.gogetme.Util.MyTextWatcher
import com.app.shopin.viewmodel.auth.MobileViewModel
import kotlinx.android.synthetic.main.activity_mobile_register.*

class MobileRegisterActivity : AppCompatActivity() , View.OnClickListener{
    private lateinit var binding: ActivityMobileRegisterBinding
    private lateinit var mobileViewModel: MobileViewModel
    lateinit var mobileno: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mobileViewModel = ViewModelProvider(this).get(MobileViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mobile_register)
        initialize()

    }
    private fun initialize()
    {
        nextBTN.setOnClickListener(this)
        emailIdLL.setOnClickListener(this)
        backIV.setOnClickListener(this)
        skipTV.setOnClickListener(this)

        mobilenoET.addTextChangedListener(
            MyTextWatcher(
                mobilenoET,
                mobileTIL,
                this)
        )

    }

    private fun registerMobile() {
        progressbarLL.visibility = View.VISIBLE
        mobileViewModel.getObserve().observe(this, {
            if (it?.status==true  && it.status_code==200) {
                progressbarLL.visibility = View.GONE
                val intent = Intent(this, OtpActivity::class.java)
                Preference.getInstance(this)?.setString(Constant.KEY_EMAILID_OR_MOBNO,mobileno)
                Preference.getInstance(this)?.setString(Constant.KEY_FROM_EMAILID_OR_MOBNO,"mobileno")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            else
            {
                progressbarLL.visibility = View.GONE
                Utils.showToast("Something Went wrong",this)
            }
        })
        mobileViewModel.makeMobileApiCall(this, mobileno)
    }

    private fun submit() {
        mobileno = mobilenoET.text.toString()
        if (mobileno.isEmpty()) {
            Utils.validateEditText("phone", mobilenoET, mobileTIL, this)
        }
        else if (mobileno.length<10)
        {
            Utils.validateEditText("phone", mobilenoET, mobileTIL, this)
        }
        else {
            Utils.hideKeyBord(this)
            registerMobile()
        }
    }

    override fun onClick(v: View?)
    {
        when (v?.id) {
            R.id.emailIdLL ->
            {
                val in7 = Intent(this, EmailRegisterActivity::class.java)
                in7.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(in7)
                overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
            }

            R.id.skipTV ->
            {
                val in7 = Intent(this, HomeActivity::class.java)
                in7.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(in7)
                overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
            }
            R.id.nextBTN ->
            {
                submit()
            }
            R.id.backIV ->
            {
                finish()
            }
        }
    }
}