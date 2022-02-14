package com.app.shopin.homePage.views.Fragment


import android.os.Bundle
import android.util.Log
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
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.FragmentHomeBinding
import com.app.shopin.homePage.Adapter.HomeBannerAdapter
import com.app.shopin.homePage.Adapter.HomeNewStoreAdapter
import com.app.shopin.homePage.models.HomeBannerListDTO
import com.app.shopin.homePage.models.HomeNewStoreListDTO
import com.app.shopin.homePage.models.StoreCategoryData
import com.app.shopin.homePage.models.StoreCategoryListResponse
import com.app.shopin.homePage.viewmodels.CategoryListViewModel
import com.app.shopin.utils.LocationMethods
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.categoryRV
import kotlinx.android.synthetic.main.fragment_home.toolbar
import kotlinx.android.synthetic.main.toolbar_home.view.*

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var categoryListViewModel: CategoryListViewModel

    companion object {
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    private fun initialize()
    {
        val returnedAddress=LocationMethods.getLocation(requireActivity())
        val addressline= returnedAddress!!.getAddressLine(0)

        toolbar.addressTV.setText(addressline)

        categoryListViewModel = ViewModelProvider(requireActivity()).get(CategoryListViewModel::class.java)
        binding.viewModel = categoryListViewModel

        categoryRV.apply {
            categoryRV.setHasFixedSize(true)
            categoryRV.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL ,false)
        }


        val users = ArrayList<HomeBannerListDTO>()
        users.add(HomeBannerListDTO("King food market", R.drawable.product))
        users.add(HomeBannerListDTO("Home Store", R.drawable.product))
        users.add(HomeBannerListDTO("Health Store", R.drawable.product))
        users.add(HomeBannerListDTO("Beauty Store", R.drawable.product))
        users.add(HomeBannerListDTO("Health and Beauty Store", R.drawable.product))

        val obj_adapter = HomeBannerAdapter(users)

        bannerRV.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        bannerRV.adapter = obj_adapter



        val homeNewStoreListDTO = ArrayList<HomeNewStoreListDTO>()
        homeNewStoreListDTO.add(HomeNewStoreListDTO("King food market","124 W,125th street New york 10 lane", R.drawable.new_store))
        homeNewStoreListDTO.add(HomeNewStoreListDTO("Home Store","124 W,125th street New york 10 lane", R.drawable.new_store))
        homeNewStoreListDTO.add(HomeNewStoreListDTO("Health Store","124 W,125th street New york 10 lane", R.drawable.new_store))
        homeNewStoreListDTO.add(HomeNewStoreListDTO("Beauty Store","124 W,125th street New york 10 lane", R.drawable.new_store))
        homeNewStoreListDTO.add(HomeNewStoreListDTO("Health and Beauty Store","124 W,125th street New york 10 lane", R.drawable.new_store))

        val homeNewStoreAdapter = HomeNewStoreAdapter(homeNewStoreListDTO)

        newstoreRV.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        newstoreRV.adapter = homeNewStoreAdapter


    }



    private fun fetchStoreCategory() {

        categoryListViewModel.getCategoryListObserver()
            .observe(this, Observer<StoreCategoryListResponse> {
                if (it != null) {

                    if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                        val categorylist: ArrayList<StoreCategoryData>? = it.data.category
                        if (categorylist != null) {

                            categoryListViewModel.setStoreAdapter(categorylist)
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


    override fun onResume() {
        super.onResume()
        fetchStoreCategory()

    }
    override fun onDestroy() {
        super.onDestroy()
        LocationMethods.stopListener()
    }


}