package com.app.shopin.homePage.views.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.app.shopin.R
import com.app.shopin.databinding.FragmentStoreDetailFeatureBinding
import kotlinx.android.synthetic.main.fragment_store_detail_feature.*


class StoreDetailFeatureFragment : Fragment() {
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
        val adapter = ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(DeliveryFragment(), "Delivery")
        adapter.addFragment(PickupFragment(), "Pickup")
        viewpager.adapter = adapter
        tabs.setupWithViewPager(binding.viewpager)
    }


    class ViewPagerAdapter(supportFragmentManager: FragmentManager) : FragmentStatePagerAdapter(
        supportFragmentManager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
    ) {

        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList.get(position)
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mFragmentTitleList[position]
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }


    }

}