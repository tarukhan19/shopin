package com.app.shopin.homePage.views.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.app.shopin.R
import com.app.shopin.UserAuth.view.EmailRegisterActivity
import com.app.shopin.databinding.FragmentPickupBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class PickupFragment : Fragment() {
    lateinit var binding : FragmentPickupBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pickup, container,
            false)
        openBottomSheetDialog()
        return binding.root
    }



    fun  openBottomSheetDialog()
    {
        val dialog = BottomSheetDialog(requireActivity())
        val view = layoutInflater.inflate(R.layout.item_bottomsheet_login_alert, null)
        val backbtn = view.findViewById<TextView>(R.id.backbtn)
        val loginBTN = view.findViewById<Button>(R.id.loginBTN)

        backbtn.setOnClickListener { dialog.dismiss() }
        loginBTN.setOnClickListener {
            val intent = Intent(requireActivity(), EmailRegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()

    }
}