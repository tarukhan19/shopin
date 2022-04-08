package com.app.shopin.homePage.views.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.FragmentDeliveryBinding
import com.app.shopin.homePage.Adapter.StoreDetailAdapter
import com.app.shopin.homePage.models.StoreCategoryData
import com.app.shopin.homePage.viewmodels.StoreDetailViewModel
import kotlinx.android.synthetic.main.fragment_delivery.*


class DeliveryFragment : Fragment() {
    lateinit var storeDetailViewModel: StoreDetailViewModel
    var id:String=""
   lateinit var storeDetailAdapter:StoreDetailAdapter

    companion object {
        @JvmStatic
        fun newInstance() =
            DeliveryFragment().apply {
                arguments = Bundle().apply {}
            }
    }
lateinit var binding : FragmentDeliveryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_delivery, container,
            false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    fun initialize()
    {
        id= requireActivity().intent.getStringExtra("id")!!
        Utils.printLog(id,"storetag")
        storeDetailViewModel = ViewModelProvider(this).get(StoreDetailViewModel::class.java)
        storeRV.apply {
            storeRV.setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity()) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun loadStore()
    {
        storeDetailViewModel.getStoreDetailObserver().observe(this) {
            if (it?.status == true && it.status_code == 200) {
                val storeprodList: ArrayList<StoreCategoryData>? = it.data.category
                if (storeprodList != null) {
                    progressbarLL.visibility = View.GONE
                    storeDetailAdapter = StoreDetailAdapter(requireActivity(),storeprodList,progressbarLL)
                    storeRV.adapter = storeDetailAdapter
                    storeDetailAdapter.notifyDataSetChanged()
                }
            } else {
                progressbarLL.visibility = View.GONE
                Utils.showToast("Something Went wrong", requireActivity())
            }
        }
        storeDetailViewModel.getStoreDetail(requireActivity(),id)
    }

    override fun onResume() {
        super.onResume()
        progressbarLL.visibility = View.VISIBLE

        loadStore()
    }
}