package com.app.shopin.homePage.views.Fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shopin.R
import com.app.shopin.UserAuth.view.EditProfileActivity
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.FragmentSearchBinding
import com.app.shopin.homePage.Adapter.AllStoreDataAdapter
import com.app.shopin.homePage.Adapter.SearchProductAdapter
import com.app.shopin.homePage.models.AllStoreDataValues
import com.app.shopin.homePage.models.StoreCategoryData
import com.app.shopin.homePage.models.StoreInventoryData
import com.app.shopin.homePage.viewmodels.SearchPageListViewModel
import com.app.shopin.homePage.views.Activity.CartPageActivity
import com.app.shopin.homePage.views.Activity.HomeActivity
import com.app.shopin.utils.Constant
import com.app.shopin.utils.OpenDialogBox
import com.app.shopin.utils.Preference
import com.app.shopin.utils.SpacesItemDecoration
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.toolbar
import kotlinx.android.synthetic.main.toolbar_search.*
import kotlinx.android.synthetic.main.toolbar_search.view.*

class SearchFragment : Fragment(),View.OnClickListener,OpenDialogBox.SearchFilterCallback {
    lateinit var binding: FragmentSearchBinding
    lateinit var searchPageListViewModel: SearchPageListViewModel
    var x = 0
     var key:Int=0
    lateinit var searchdata:String
    private lateinit var storeListAdapter: AllStoreDataAdapter
    private lateinit var ctx: HomeActivity
    var isLogin:Boolean=false

    companion object
    {
        @JvmStatic
        fun newInstance() =
            SearchFragment().apply{
                arguments = Bundle().apply {
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View
    {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_search,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)
    {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initialize()
    {
        isLogin=Preference.getInstance(requireActivity())?.getBoolean(Constant.IS_LOGIN)!!
        if (isLogin)
        {
            mainLL.visibility=View.VISIBLE
        }
        else
        {
            mainLL.visibility=View.GONE
            ctx.openBottomSheetDialog(requireActivity())

        }
        searchPageListViewModel =
            ViewModelProvider(requireActivity()).get(SearchPageListViewModel::class.java)
        binding.itemViewModel = searchPageListViewModel
        binding.lifecycleOwner = this


        toolbar.titleTV.text = getString(R.string.search)
        Preference.getInstance(requireActivity())?.setInt(Constant.EXTERNAL_SEARCH_FILTER,Constant.FILTER_CATEGORY)
        key= Preference.getInstance(requireActivity())?.getInt(Constant.EXTERNAL_SEARCH_FILTER)!!
        x = (resources.displayMetrics.density * 4).toInt()

        filterIV.setOnClickListener(this)
        addtocartLL.setOnClickListener(this)


        searchcategRecycler.addItemDecoration(SpacesItemDecoration(x))
        searchcategRecycler.apply {
            searchcategRecycler.setHasFixedSize(true)
            searchcategRecycler.isNestedScrollingEnabled = false
            val gridLayoutManager = GridLayoutManager(context, 2)
            gridLayoutManager.orientation = GridLayoutManager.VERTICAL
            searchcategRecycler.layoutManager = gridLayoutManager
        }

        searchstoreRecycler.apply {
            searchstoreRecycler.setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireActivity())
        }

        binding.searchET.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isEmpty())
                {
                    getSearchFilter()
                }
                else
                {
                    searchdata=s.toString()
                    fetchSearchData()
                }


            }
        })


    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchStoreCategory()
    {
        searchPageListViewModel.categoryListLiveData.removeObservers(this)
        searchPageListViewModel.getSearchPageListObserver()
            .observe(this) {
                if (it != null)
                {
                    if (key==3) {
                        val categorylist: ArrayList<StoreCategoryData>? = it.data.category
                        if (categorylist != null) {
                            searchPageListViewModel.setCategoryAdapter(categorylist)
                        }
                    }
                    else if (key==1) {
                        val storeList: ArrayList<AllStoreDataValues>? = it.data.store
                        Log.e("storeList",storeList.toString())

                        if (storeList != null) {
                            storeListAdapter = AllStoreDataAdapter(storeList,false)
                            searchstoreRecycler.adapter = storeListAdapter
                            storeListAdapter.notifyDataSetChanged()

                        }
                    }
                    else if (key==2) {
                        val storeprodList: ArrayList<AllStoreDataValues>? = it.data.store_product
                        Log.e("storeprodList",storeprodList.toString())

                        if (storeprodList != null) {
                            storeListAdapter = AllStoreDataAdapter(storeprodList,false)
                            searchstoreRecycler.adapter = storeListAdapter
                            storeListAdapter.notifyDataSetChanged()
                        }
                    }
                    else {
                        Toast.makeText(
                            requireContext(),
                            "Something wrong with api",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }

        searchPageListViewModel.getCategoryList(requireContext(),key)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchSearchData()
    {
        searchPageListViewModel.categoryListLiveData.removeObservers(this)
        searchPageListViewModel.searchListLiveData.removeObservers(this)

        searchPageListViewModel.getSearchDataListObserver()
            .observe(this) {
                if (it != null)
                {
                    if (key==3) {
                        val categorylist: ArrayList<StoreCategoryData>? = it.data.category
                        if (categorylist != null) {
                            searchPageListViewModel.setCategoryAdapter(categorylist)
                        }
                    }
                    else if (key==1) {
                        val storeList: ArrayList<AllStoreDataValues>? = it.data.stores
                        if (storeList != null) {

                            storeListAdapter = AllStoreDataAdapter(storeList,true)
                            searchstoreRecycler.adapter = storeListAdapter
                            storeListAdapter.notifyDataSetChanged()

                        }
                    }
                    else if (key==2) {
                        val storeprodList: ArrayList<AllStoreDataValues>? = it.data.stores_product

                        if (storeprodList != null) {
                            storeListAdapter = AllStoreDataAdapter(storeprodList,true)
                            searchstoreRecycler.adapter = storeListAdapter
                            storeListAdapter.notifyDataSetChanged()                        }
                    }
                    else {
                        Toast.makeText(
                            requireContext(),
                            "Something wrong with api",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
            }

        searchPageListViewModel.getSearchList(requireContext(),key,searchdata)
    }


    override fun onResume() {
        super.onResume()
        fetchStoreCategory()
    }



    override fun onClick(v: View?) {
        when (v?.id)
        {
            R.id.filterIV ->
            {
                if (isLogin)
                {
                    searchdata=""
                    searchET.setText("")
                    searchET.setHint("Search for items or stores")
                    OpenDialogBox.openFilterDialog(this,requireActivity())
                }
                else
                {
                    ctx.openBottomSheetDialog(requireActivity())

                }

            }

            R.id.addtocartLL -> {
                if (isLogin)
                {
                    val in7 = Intent(requireActivity(), CartPageActivity::class.java)
                    startActivity(in7)
                }
                else
                {
                    ctx.openBottomSheetDialog(requireContext())
                }

            }
        }
    }

    override fun getSearchFilter() {
        key= Preference.getInstance(requireActivity())?.getInt(Constant.EXTERNAL_SEARCH_FILTER)!!
        hideShowView()
    }

    fun hideShowView()
    {
        if (key==1)
        {
            headerTV.setText(getString(R.string.all_store))
            searchstoreRecycler.visibility=View.VISIBLE
            searchcategRecycler.visibility=View.GONE
        }
        else if(key==2)
        {
            headerTV.setText(getString(R.string.all_product))
            searchstoreRecycler.visibility=View.VISIBLE
            searchcategRecycler.visibility=View.GONE
        }
        else if(key==3)
        {
            headerTV.setText(getString(R.string.all_categories))
            searchstoreRecycler.visibility=View.GONE
            searchcategRecycler.visibility=View.VISIBLE
        }
        fetchStoreCategory()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context as HomeActivity

    }


}