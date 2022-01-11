package com.app.shopin.UserAuth.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.shopin.HomePage.view.HomeActivity
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ActivityEmailRegisterBinding
import com.app.shopin.utils.Constant
import com.app.shopin.utils.Preference
import com.customer.gogetme.Util.MyTextWatcher
import com.app.shopin.viewmodel.auth.EmailViewModel
import kotlinx.android.synthetic.main.activity_email_register.*

class EmailRegisterActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityEmailRegisterBinding
    private lateinit var emailViewModel: EmailViewModel
    lateinit var emailID: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email_register)
        initialize()

    }

    private fun initialize() {
        emailViewModel = ViewModelProvider(this).get(EmailViewModel::class.java)
        nextBTN.setOnClickListener(this)
        mobileNoLL.setOnClickListener(this)
        backIV.setOnClickListener(this)
        skipTV.setOnClickListener(this)
        emailidET.addTextChangedListener(
            MyTextWatcher(
                emailidET,
                emailTIL,
                this
            )
        )
    }

    private fun registerEmail() {
        progressbarLL.visibility = View.VISIBLE
        emailViewModel.getObserve().observe(this, {

            if (it?.status==true  && it.status_code==200) {
                progressbarLL.visibility = View.GONE
                val intent = Intent(this@EmailRegisterActivity, OtpActivity::class.java)
                Preference.getInstance(this)?.setString(Constant.KEY_EMAILID_OR_MOBNO,emailID)
                Preference.getInstance(this)?.setString(Constant.KEY_FROM_EMAILID_OR_MOBNO,"emailid")
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            else
            {
                progressbarLL.visibility = View.GONE
                Utils.showToast("Something Went wrong",this)
            }
        })
        emailViewModel.makeEmailApiCall(this, emailID)
    }

    private fun submit() {
        emailID = emailidET.text.toString()
        if (emailID.isEmpty()) {
            Utils.validateEditText("id", emailidET, emailTIL, this)
        }
        else if (!Utils.isValidEmail(emailID)) {
            Utils.validateEditText("id", emailidET, emailTIL, this)
        }
        else {
            Utils.hideKeyBord(this)
            registerEmail()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.mobileNoLL -> {
                val in7 = Intent(this, MobileRegisterActivity::class.java)
                in7.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(in7)
                overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
            }

            R.id.skipTV -> {
                val in7 = Intent(this, HomeActivity::class.java)
                in7.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(in7)
                overridePendingTransition(R.anim.no_animation, R.anim.no_animation)
            }
            R.id.nextBTN -> {
                submit()
            }
            R.id.backIV ->
            {
                finish()
            }
        }
    }
}

