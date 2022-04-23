package com.app.shopin.utils

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import com.app.shopin.R
import com.app.shopin.UserAuth.view.EditProfileActivity
import com.app.shopin.UserAuth.view.WelcomeToShop
import com.app.shopin.databinding.ItemFilterBinding
import com.app.shopin.databinding.ItemLogoutBinding
import com.app.shopin.databinding.ItemSuccessDialogBinding
import com.app.shopin.homePage.views.Activity.DeliveryAddressAddActivity
import com.app.shopin.homePage.views.Activity.DeliveryAddressUpdateActivity
import com.app.shopin.homePage.views.Activity.GetMyStoreListedActivity
import com.app.shopin.homePage.views.Fragment.SearchFragment


class OpenDialogBox {
    companion object
    {
        fun openLogoutDialog(ctx: Context)
        {
            val dialog = Dialog(ctx) // Context, this, etc.
            val itemDataBinding: ItemLogoutBinding = DataBindingUtil.inflate(
                LayoutInflater.from(dialog.context),
                R.layout.item_logout,
                null,
                false
            )
            dialog.window!!.attributes.windowAnimations = R.style.CustomDialogFragmentAnimation
            dialog.setContentView(itemDataBinding.root)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            dialog.show()

            itemDataBinding.cancelBTN.setOnClickListener {

                dialog.dismiss()

            }

            itemDataBinding.logOutBTN.setOnClickListener {
                Preference.getInstance(ctx)?.clearAll()

                dialog.dismiss()

                val intent = Intent(ctx, WelcomeToShop::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                ctx.startActivity(intent)
            }
        }



        fun openDialog(ctx: Context, title: String, msg: String, from: String)
        {
            val dialog = Dialog(ctx, R.style.CustomDialog)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            val itemDataBinding: ItemSuccessDialogBinding = DataBindingUtil.inflate(
                LayoutInflater.from(dialog.context),
                R.layout.item_success_dialog,
                null,
                false
            )
            dialog.setContentView(itemDataBinding.root)
            dialog.setCanceledOnTouchOutside(false)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()

            itemDataBinding.titleTV.setText(title)
            itemDataBinding.msgTV.setText(msg)
            itemDataBinding.okBTN.setOnClickListener {
                dialog.dismiss()
                if (from.equals("getmystorelisted"))
                {
                    GetMyStoreListedActivity.getInstance()?.runThread()
                }
                else if (from.equals("addaddress"))
                {
                    DeliveryAddressAddActivity.getInstance()?.runThread()
                }
                else if (from.equals("editprofile"))
                {
                    EditProfileActivity.getInstance()?.runThread()
                }
                else if (from.equals("updateaddress"))
                {
                    DeliveryAddressUpdateActivity.getInstance()?.runThread()
                }
                else if (from.equals("issue"))
                {
                   // CartPageActivity.getInstance()?.runThread("dialog","")
                }
            }
        }


        fun openFilterDialog(context: SearchFragment, ctx: Context)
        {
            val dialog = Dialog(ctx) // Context, this, etc.
            val itemDataBinding: ItemFilterBinding = DataBindingUtil.inflate(
                LayoutInflater.from(dialog.context),
                R.layout.item_filter,
                null,
                false
            )
            dialog.setContentView(itemDataBinding.root)
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.WHITE))
            dialog.window!!.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            dialog.setCancelable(false)
            dialog.show()

           val key= Preference.getInstance(ctx)?.getInt(Constant.EXTERNAL_SEARCH_FILTER)!!
            if (key==1)
            {
                itemDataBinding.storeRB.isChecked=true
            }
            else if (key==2)
            {
                itemDataBinding.productRB.isChecked=true

            }
            else if (key==3)
            {
                itemDataBinding.categoryRB.isChecked=true

            }


            itemDataBinding.toolbar.titleTV.setText("Apply Filter")
            itemDataBinding.toolbar.backLL.setOnClickListener{
                dialog.dismiss()
            }

            itemDataBinding.cancelBTN.setOnClickListener {

                dialog.dismiss()

            }

            itemDataBinding.radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId -> // checkedId is the RadioButton selected
                when (checkedId) {
                    R.id.categoryRB -> {
                        Preference.getInstance(ctx)?.setInt(Constant.EXTERNAL_SEARCH_FILTER,Constant.FILTER_CATEGORY)

                    }
                    R.id.storeRB -> {
                        Preference.getInstance(ctx)?.setInt(Constant.EXTERNAL_SEARCH_FILTER,Constant.FILTER_STORE)

                    }
                    R.id.productRB -> {
                        Preference.getInstance(ctx)?.setInt(Constant.EXTERNAL_SEARCH_FILTER,Constant.FILTER_PRODUCT)

                    }
                }
            })

            itemDataBinding.applyBTN.setOnClickListener {
                val listener = context as SearchFilterCallback
                listener.getSearchFilter()
                dialog.dismiss()
            }
        }

    }



    interface SearchFilterCallback {
        fun getSearchFilter()
    }



}