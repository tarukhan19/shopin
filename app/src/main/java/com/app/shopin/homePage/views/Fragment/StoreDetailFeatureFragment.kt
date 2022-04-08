package com.app.shopin.homePage.views.Fragment

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.shopin.R
import com.app.shopin.UserAuth.view.EmailRegisterActivity
import com.app.shopin.databinding.FragmentStoreDetailFeatureBinding
import com.app.shopin.utils.Constant
import com.app.shopin.utils.Preference
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.fragment_store_detail_feature.*


class StoreDetailFeatureFragment : Fragment(),View.OnClickListener
{
    lateinit var binding:FragmentStoreDetailFeatureBinding
    companion object {
        @JvmStatic
        fun newInstance() =
            StoreDetailFeatureFragment().apply {
                arguments = Bundle().apply {}
            }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_store_detail_feature, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }
    private fun initialize()

    {
        deliveryTV.setOnClickListener(this)
        pickupTV.setOnClickListener(this)

        Preference.getInstance(requireActivity())?.setString(Constant.INSTORE_OR_DELIVERY,Constant.DELIVERY)

        fragmentContainer.visibility=View.VISIBLE
        addFragment(DeliveryFragment.newInstance())
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.deliveryTV -> {
                fragmentContainer.visibility=View.VISIBLE
                Preference.getInstance(requireActivity())?.setString(Constant.INSTORE_OR_DELIVERY,Constant.DELIVERY)
                   deliveryTV.setBackgroundResource(R.drawable.tabackgrnd)
                   pickupTV.setBackgroundResource(0)

            }

            R.id.pickupTV -> {
                Preference.getInstance(requireActivity())?.setString(Constant.INSTORE_OR_DELIVERY,Constant.PICKUP)
                fragmentContainer.visibility=View.VISIBLE
                    pickupTV.setBackgroundResource(R.drawable.tabackgrnd)
                    deliveryTV.setBackgroundResource(0)

            }
        }
    }


    private fun addFragment(fragment: Fragment){
        val fragmentTransition = childFragmentManager.beginTransaction()

            fragmentTransition.add(R.id.fragmentContainer,fragment).commit()

    }



}