package com.app.shopin.homePage.views.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.DemoStoreFragmentBinding
import com.app.shopin.homePage.models.*
import com.app.shopin.homePage.viewmodels.AllStoreListViewModel
import com.app.shopin.homePage.viewmodels.CategoryListViewModel
import kotlinx.android.synthetic.main.demo_store_fragment.*
import kotlinx.android.synthetic.main.toolbar.view.*


class StoreFragment : Fragment() {
    lateinit var binding:DemoStoreFragmentBinding
    lateinit var categoryListViewModel: CategoryListViewModel
  //  lateinit var allStoreListViewModel: AllStoreListViewModel
    companion object {
        @JvmStatic
        fun newInstance() =
            StoreFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.demo_store_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }


    private fun initialize()
    {
        // category data
        categoryListViewModel = ViewModelProvider(requireActivity()).get(CategoryListViewModel::class.java)
        binding.viewModel = categoryListViewModel
        toolbar.titleTV.text = getString(R.string.store)
        categoryRV.apply {
            categoryRV.setHasFixedSize(true)
            categoryRV.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL ,false)
        }


//
//        // all store data
//        allStoreListViewModel = ViewModelProvider(requireActivity()).get(AllStoreListViewModel::class.java)
//        binding.allstoreviewModel = allStoreListViewModel
//        categoryRV.apply {
//            storeRV.setHasFixedSize(true)
//            storeRV.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL ,false)
//        }

    }


    private fun fetchStoreCategory() {

        categoryListViewModel.getCategoryListObserver()
            .observe(this, Observer<StoreCategoryListResponse> {
                if (it != null) {

                    if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                        val categorylist: ArrayList<StoreCategoryData>? = it.data.category
                        if (categorylist != null) {

                            categoryListViewModel.setStoreAdapter(categorylist)
                           // allStoreCategory()
                        } else {
                            Toast.makeText(
                                requireContext(),
                                "Something wrong with api",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
            })

        categoryListViewModel.getCategoryList(requireContext())
    }


//
//    private fun allStoreCategory() {
//
//        allStoreListViewModel.getAllStoreListObserver()
//            .observe(this, Observer<AllStoreListResponse> {
//                if (it != null) {
//
//                    if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
//                        val allStoreCategoryData: ArrayList<AllStoreCategoryData>? = it.data.store
//                        Utils.printLog(allStoreCategoryData?.size.toString(), "categorylist")
//                        if (allStoreCategoryData != null) {
//
//                            allStoreListViewModel.setAllStoreAdapter(allStoreCategoryData,requireActivity())
//
//                        } else {
//                            Toast.makeText(
//                                requireContext(),
//                                "Something wrong with api",
//                                Toast.LENGTH_SHORT
//                            ).show()
//
//                        }
//                    }
//                }
//            })
//
//        allStoreListViewModel.getAllStoryList(requireContext())
//    }

    override fun onResume() {
        super.onResume()
        fetchStoreCategory()

    }


}