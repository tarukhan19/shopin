package com.app.shopin.UserAuth.view

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.shopin.HomePage.view.HomeActivity
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ActivityOtpBinding
import com.app.shopin.utils.Constant
import com.app.shopin.utils.Preference
import com.app.shopin.viewmodel.auth.EmailViewModel
import com.app.shopin.viewmodel.auth.MobileViewModel
import com.app.shopin.viewmodel.auth.OtpViewModel
import kotlinx.android.synthetic.main.activity_email_register.*
import kotlinx.android.synthetic.main.activity_mobile_register.*
import kotlinx.android.synthetic.main.activity_otp.*
import kotlinx.android.synthetic.main.activity_otp.nextBTN
import kotlinx.android.synthetic.main.activity_otp.progressbarLL

class OtpActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var otp:String
    private lateinit var emailOrMobile:String
    private lateinit var binding:ActivityOtpBinding
    private lateinit var otpViewModel : OtpViewModel
    private lateinit var emailViewModel : EmailViewModel
    private lateinit var mobileViewModel: MobileViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_otp)
        initialize()
    }

    private fun initialize()
    {
        otpViewModel = ViewModelProvider(this).get(OtpViewModel::class.java)
        mobileViewModel = ViewModelProvider(this).get(MobileViewModel::class.java)
        emailViewModel = ViewModelProvider(this).get(EmailViewModel::class.java)
        emailOrMobile = Preference.getInstance(this)?.getString(Constant.KEY_EMAILID_OR_MOBNO)!!

        resendOtp.setOnClickListener(this)
        nextBTN.setOnClickListener(this)

        binding.otpET1.addTextChangedListener(
            OtpWatcher(
                otpET1,
                this,binding
            )
        )

        binding.otpET2.addTextChangedListener(
            OtpWatcher(
                otpET2,
                this,binding
            )
        )
        binding.otpET3.addTextChangedListener(
            OtpWatcher(
                otpET3, this,binding
            )
        )


        binding.otpET4.addTextChangedListener(
            OtpWatcher(
                otpET4, this,binding
            )
        )
        binding.otpET5.addTextChangedListener(
            OtpWatcher(
                otpET5, this,binding
            )
        )
        binding.otpET6.addTextChangedListener(
            OtpWatcher(
                otpET6,
                this,binding
            )
        )

    }


    class OtpWatcher(
        var view: View,
        var activity: Activity,
        var binding: ActivityOtpBinding
    ) : TextWatcher {


        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable) {

            when (view.id) {

                R.id.otpET1 -> {
                    Utils.validateEditText("otp", binding.otpET1, binding.otpTIL, activity)

                    if (s.length==1)
                    {
                        binding.otpET2.requestFocus()
                    }

                }
                R.id.otpET2 -> {
                    Utils.validateEditText("otp", binding.otpET2, binding.otpTIL, activity)

                    if (s.length==1)
                    {
                        binding.otpET3
                            .requestFocus()
                    }
                    else if (s.length==0)
                    {
                        binding.otpET1.requestFocus();
                    }

                }
                R.id.otpET3 -> {
                    Utils.validateEditText("otp", binding.otpET3, binding.otpTIL, activity)

                    if (s.length==1)
                    {
                        binding.otpET4.requestFocus()
                    }
                    else if (s.length==0)
                    {
                        binding.otpET2.requestFocus();
                    }

                }

                R.id.otpET4 -> {
                    Utils.validateEditText("otp", binding.otpET4, binding.otpTIL, activity)

                    if (s.length==1)
                    {
                        binding.otpET5.requestFocus()
                    }
                    else if (s.length==0)
                    {
                        binding.otpET3.requestFocus();
                    }

                }

                R.id.otpET5 -> {
                    Utils.validateEditText("otp", binding.otpET5, binding.otpTIL, activity)

                    if (s.length==1)
                    {
                        binding.otpET6.requestFocus()
                    }
                    else if (s.length==0)
                    {
                        binding.otpET4.requestFocus();
                    }

                }

                R.id.otpET6 -> {
                    Utils.validateEditText("otp", binding.otpET6, binding.otpTIL, activity)

                    if (s.length==0)
                    {
                        binding.otpET5.requestFocus()
                    }

                }


            }

        }

    }

    private fun verifyEmailWithOtp() {

        progressbarLL.visibility = View.VISIBLE
        otpViewModel.getObserveData().observe(this,{

            if (it?.status==true  && it.status_code==200) {
                progressbarLL.visibility = View.GONE
                Preference.getInstance(this)?.setboolean(Constant.IS_LOGIN,true)
                Preference.getInstance(this)?.setString(Constant.KEY_TOKEN,it.otpResponseItems.token)
                Preference.getInstance(this)?.setString(Constant.KEY_EMAIL_ID,it.otpResponseItems.email)
                Preference.getInstance(this)?.setString(Constant.KEY_MOBILE_NO,it.otpResponseItems.phone_no)
                Preference.getInstance(this)?.setString(Constant.KEY_USER_ID,it.otpResponseItems.user_id)

                val intent = Intent(this@OtpActivity, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            else
            {
                progressbarLL.visibility = View.GONE
                Utils.showToast(it?.msg.toString(),this)
            }
        })
        otpViewModel.makeOtpApiCall(this,emailOrMobile,otp)
    }

    private fun resendOtp() {
        progressbarLL.visibility = View.VISIBLE

        if (Preference.getInstance(this)?.getString(Constant.KEY_FROM_EMAILID_OR_MOBNO).equals("mobileno"))
        {
            mobileViewModel.getObserve().observe(this, {
                if (it?.status==true  && it.status_code==200) {
                    progressbarLL.visibility = View.GONE
                    Utils.showToast("OTP sent",this)
                }
                else
                {
                    progressbarLL.visibility = View.GONE
                    Utils.showToast("Something Went wrong",this)
                }
            })
            mobileViewModel.makeMobileApiCall(this, emailOrMobile)
        }
        else
        {
                emailViewModel.getObserve().observe(this, {
                    if (it?.status==true  && it.status_code==200) {
                        progressbarLL.visibility = View.GONE
                        Utils.showToast("OTP sent",this)
                    }
                    else
                    {
                        progressbarLL.visibility = View.GONE
                        Utils.showToast("Something Went wrong",this)
                    }
                })
                emailViewModel.makeEmailApiCall(this, emailOrMobile)


        }

    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.resendOtp ->{
                otpET1.setText(" ")
                otpET2.setText(" ")
                otpET3.setText(" ")
                otpET4.setText(" ")
                otpET5.setText(" ")
                otpET6.setText(" ")
                otpET1.requestFocus()
                otp=" "

                resendOtp()
            }
            R.id.nextBTN ->{
                submit()
            }
            R.id.backIV ->{
                finish()
            }
        }
    }


    private fun submit()
    {

        val otp1=otpET1.text.toString()
        val otp2=otpET2.text.toString()
        val otp3=otpET3.text.toString()
        val otp4=otpET4.text.toString()
        val otp5=otpET5.text.toString()
        val otp6=otpET6.text.toString()

        if (otp1.isEmpty())
        {
            Utils.validateEditText("otp", binding.otpET1, binding.otpTIL, this)

        }
        else if (otp2.isEmpty())

        {
            Utils.validateEditText("otp", binding.otpET2, binding.otpTIL, this)

        }
        else if (otp3.isEmpty())

        {
            Utils.validateEditText("otp", binding.otpET3, binding.otpTIL, this)

        }
        else if (otp4.isEmpty())

        {
            Utils.validateEditText("otp", binding.otpET4, binding.otpTIL, this)

        }

        else if (otp5.isEmpty())

        {
            Utils.validateEditText("otp", binding.otpET5, binding.otpTIL, this)

        }

        else if (otp6.isEmpty())

        {
            Utils.validateEditText("otp", binding.otpET6, binding.otpTIL, this)

        }


        else
        {
            binding.progressbarLL.visibility= View.VISIBLE
            otp=otp1+otp2+otp3+otp4+otp5+otp6
            Utils.hideKeyBord(this)
            verifyEmailWithOtp()

        }

    }
}