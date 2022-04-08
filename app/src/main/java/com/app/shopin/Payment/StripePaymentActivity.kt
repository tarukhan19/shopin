package com.app.shopin.Payment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.shopin.R
import com.app.shopin.databinding.ActivityStripePaymentBinding
import com.app.shopin.utils.Constant
import com.futuremind.recyclerviewfastscroll.Utils
import com.google.gson.Gson
import com.stripe.android.Stripe
import com.stripe.android.model.Card
import com.stripe.android.model.CardBrand
import com.stripe.android.model.CardParams
import com.stripe.android.model.Token

class StripePaymentActivity : AppCompatActivity() {

    private lateinit var mStripe: Stripe
    private var cardInput: String? = null
    private var cardName: String? = null
    var keyDel = 0
    private val gson: Gson = Gson()
   // private lateinit var vm: PaymentVM
    private lateinit var binding: ActivityStripePaymentBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  vm = ViewModelProviders.of(this).get(PaymentVM::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stripe_payment)
      //  cartRes = gson.fromJson(intent.getStringExtra(Constants.CART_DATA), CartResponse::class.java)
        mStripe = Stripe(applicationContext, Constant.publishableTestKey)
       // listeners()
    }

//    private fun listeners()
//    {
//        binding.editCardnumber.addTextChangedListener(mCardNumberTextWatcher)
//        binding.editMonth.addTextChangedListener(mCreditCardExpireYearTextWatcher)
//
//        binding.btnPay.setOnClickListener(this)
//        binding.toolbar.backLL.setOnClickListener(this)
//        binding.toolbar.titleTV.setText("Payment")
//    }
//
//
//
//    private val mCardNumberTextWatcher = object : TextWatcher {
//        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
//
//        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//            var flag = true
//
//            if (before <= 18) {
//                val eachBlock = binding.editCardnumber.text!!.toString().split("-".toRegex())
//                    .dropLastWhile { it.isEmpty() }.toTypedArray()
//                for (i in eachBlock.indices) {
//                    if (eachBlock[i].length > 4) {
//                        flag = false
//                    }
//                }
//                if (flag) {
//
//                    binding.editCardnumber.setOnKeyListener { v, keyCode, event ->
//                        if (keyCode == KeyEvent.KEYCODE_DEL)
//                            keyDel = 1
//                        false
//                    }
//
//                    if (keyDel == 0) {
//
//                        if ((binding.editCardnumber.text!!.length + 1) % 5 == 0) {
//                            //binding.editCardnumber.setText("${binding.editCardnumber.getText()}-")
//                            binding.editCardnumber.setText(
//                                binding.editCardnumber.text.toString().plus(
//                                    "-"
//                                )
//                            )
//                            if (binding.editCardnumber.text!!.toString().split("-".toRegex())
//                                    .dropLastWhile
//                                    { it.isEmpty() }.toTypedArray().size <= 3
//                            ) {
//                                binding.editCardnumber.text = binding.editCardnumber.text!!.append(
//                                    "-"
//                                )
//                                binding.editCardnumber.setSelection(binding.editCardnumber.text!!.length)
//                            }
//                        }
//                        cardInput = binding.editCardnumber.text!!.toString()
//                        binding.editCardnumber.setSelection(binding.editCardnumber.text!!.length)
//                    } else {
//                        cardInput = binding.editCardnumber.text!!.toString()
//                        keyDel = 0
//                    }
//
//                } else {
//                    binding.editCardnumber.setText(cardInput)
//                    binding.editCardnumber.setSelection(binding.editCardnumber.text!!.length)
//                }
//            }
//
//        }
//
//        override fun afterTextChanged(s: Editable) {
//            if (binding.editCardnumber.text!!.isNotEmpty()) {
//
//                val mmCard = Card(binding.editCardnumber.text!!.toString(), "0", 0, 0)
//                    when (mmCard.brand) {
//                        CardBrand.Visa -> {
//                        binding.stripeCardIcon.setImageResource(R.drawable.ic__visa)
//                        binding.stripeCardIcon.visibility = View.VISIBLE
//                        cardName = "VISA"
//                    }
//                        CardBrand.MasterCard -> {
//                        binding.stripeCardIcon.setImageResource(R.drawable.ic__mastercard)
//                        binding.stripeCardIcon.visibility = View.VISIBLE
//                        cardName = "MASTER CARD"
//                    }
//                        CardBrand.AmericanExpress -> {
//                        binding.stripeCardIcon.setImageResource(R.drawable.ic__ae)
//                        binding.stripeCardIcon.visibility = View.VISIBLE
//                        cardName = "AMERICAN EXPRESS"
//                    }
//                    else -> {
//                        binding.stripeCardIcon.visibility = View.GONE
//                        cardName = "OTHER"
//                    }
//                }
//            } else {
//                binding.stripeCardIcon.visibility = View.GONE
//            }
//        }
//    }
//
//
//    private val mCreditCardExpireYearTextWatcher = object : TextWatcher {
//        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
//
//        }
//
//        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//
//        }
//
//        override fun afterTextChanged(s: Editable)
//        {
//            val input = s.toString()
//            if (s.length == 2) {
//                val month = Integer.parseInt(input)
//                if (month <= 12 && month != 0) {
//                    binding.editYear.requestFocus()
//                } else {
//                    binding.editMonth.setText(
//                        binding.editMonth.text.toString().substring(
//                            0,
//                            1
//                        )
//                    )
//                    binding.editMonth.setSelection(binding.editMonth.text.toString().length)
//                }
//            } else if (s.length == 1) {
//                val month = Integer.parseInt(input)
//                if (month > 1) {
//                    // binding.editMonth.setText("0" + binding.editMonth.getText().toString())
//                    binding.editMonth.setText("0".plus(binding.editMonth.text.toString()))
//                    binding.editYear.requestFocus()
//                }
//            }
//            return
//        }
//    }
//
//    override fun onClick(v: View?) {
//
//        when (v?.id) {
//            R.id.btn_pay -> makePayment()
//
//            R.id.back_LL -> onBackPressed()
//        }
//    }
//
//    private fun makePayment()
//    {
//        if (binding.editCardnumber.text!!.toString().isEmpty()) {
//            binding.editCardnumber.requestFocus()
//            binding.editCardnumber.error = getString(R.string.__stripe_invalidate_card_number)
//            return
//        }
//        if (binding.editMonth.text.toString().isEmpty()) {
//            binding.editMonth.requestFocus()
//            binding.editMonth.error = getString(R.string.__stripe_invalidate_expirymonth)
//            return
//        }
//        if (binding.editYear.text.toString().isEmpty()) {
//            binding.editYear.requestFocus()
//            binding.editYear.error = getString(R.string.__stripe_invalidate_expiryyear)
//            return
//        }
//        if (binding.editCvv.text.toString().isEmpty()) {
//            binding.editCvv.requestFocus()
//            binding.editCvv.error = getString(R.string.__stripe_invalidate_cvc)
//            return
//        }
//        if (binding.edtCardholderName.text.toString().isEmpty()) {
//             binding.edtCardholderName.requestFocus()
//             binding.edtCardholderName.error = getString(R.string.__stripe_invalidate_holder_name)
////            Utils.validateEditText(
////                "cardHolderName",
////                binding.edtCardholderName,
////                binding.tilCardholderName,
////                this
////            )
//            return
//        }
//
//        val mCard = Card(
//            binding.editCardnumber.text.toString(),
//            Integer.parseInt(binding.editMonth.text.toString()),
//            Integer.parseInt(binding.editYear.text.toString()),
//            binding.editCvv.text.toString()
//        )
//
//        if (mCard.validateNumber()) {
//
//            val pd = SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE)
//            pd.progressHelper.barColor = resources.getColor(R.color.colorPrimary)
//            pd.progressHelper.barWidth = 2
//            pd.progressHelper.rimColor = resources.getColor(R.color.colorAccent)
//            pd.titleText = getString(R.string.connecting_stripe)
//            pd.setCancelable(false)
//            pd.show()
//
//
//            mStripe.createCardToken(mCard, object : TokenCallback {
//                override fun onError(error: java.lang.Exception?) {
//
//                    error?.message?.let {
//                        if (it.isNotEmpty()) {
//                            Log.e("error", error.message.toString())
//                            pd.dismissWithAnimation()
//                            Utils.showToastL(this@StripePaymentActivity, it)
//                        }
//                    }
//                }
//                override fun onSuccess(token: Token?) {
//                    pd.dismiss()
//                    token?.let {
//                        Log.e("token",it.id.toString())
//                        vm.payBill(
//                            this@StripePaymentActivity,
//                            cartRes!!.data.orderId,
//                            cartRes!!.data.channel,
//                            3,
//                            cartRes!!.data.finalTotal, //order amount + sales tax + gratuity
//                            it.id,
//                            cartRes!!.data.gratuity, // gratuity
//                            cartRes!!.data.orderTotal,// order total excluding all taxes
//                            cartRes!!.data.salesTaxValue // gratuity
//                        )
//
//                    }
//                }
//
//            })
//        } else if (!mCard.validateNumber()) {
//            binding.editCardnumber.requestFocus()
//            binding.editCardnumber.error = getString(R.string.__stripe_invalidate_card_number)
//        } else if (!mCard.validateExpiryDate()) {
//            binding.editYear.requestFocus()
//            binding.editYear.error = getString(R.string.__stripe_invalidate_expiryyear)
//        } else if (!mCard.validateCVC()) {
//            binding.editCvv.requestFocus()
//            binding.editCvv.error = getString(R.string.__stripe_invalidate_cvc)
//        }
//    }


}
