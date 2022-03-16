package com.app.shopin.homePage.views.Activity

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.shopin.R
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ActivityGetMyStoreListedBinding
import com.app.shopin.databinding.ItemStorecategoryRecycleviewBinding
import com.app.shopin.homePage.Adapter.StoreCategoryAdapter
import com.app.shopin.homePage.models.StoreCategoryData
import com.app.shopin.homePage.models.StoreCategoryListResponse
import com.app.shopin.homePage.viewmodels.CategoryListViewModel
import com.app.shopin.homePage.viewmodels.GetMyStoreListedViewModel
import com.app.shopin.utils.OpenDialogBox
import com.customer.gogetme.Util.MyTextWatcher
import kotlinx.android.synthetic.main.activity_get_my_store_listed.*
import kotlinx.android.synthetic.main.toolbar.view.*

class GetMyStoreListedActivity : AppCompatActivity(), View.OnClickListener,StoreCategoryAdapter.StoreCategoryCallback {
    lateinit var binding: ActivityGetMyStoreListedBinding
    lateinit var personname: String
    lateinit var businessname: String
    lateinit var businessaddress: String
    lateinit var mobile: String
    lateinit var emailid: String
    var categoryValue: String = ""
     var categoryID: Int = 0
    lateinit var categdialog: Dialog
    lateinit var categrv: RecyclerView
    lateinit var categalertdialog: AlertDialog.Builder
    lateinit var categView: View
    lateinit var categoryListViewModel: CategoryListViewModel
    private lateinit var getMyStoreListedViewModel: GetMyStoreListedViewModel

    companion object {
        @SuppressLint("StaticFieldLeak")
        var getMyStoreListedActivity: GetMyStoreListedActivity? = null
        fun getInstance(): GetMyStoreListedActivity? {
            return getMyStoreListedActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_get_my_store_listed)
        getMyStoreListedActivity = this

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.submitBTN -> {
                submit()
            }
            R.id.back_LL -> {
                finish()
            }
            R.id.categorytypeLL -> {
                categalertdialog.setView(categrv)
                categdialog.show()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        initialize()
    }

    private fun initialize()
    {
        getMyStoreListedViewModel =
            ViewModelProvider(this).get(GetMyStoreListedViewModel::class.java)
        categoryListViewModel = ViewModelProvider(this).get(CategoryListViewModel::class.java)
        toolbar.titleTV.text = getString(R.string.get_my_store_listed)
        toolbar.back_LL.setOnClickListener(this)
        categorytypeLL.setOnClickListener(this)
        submitBTN.setOnClickListener(this)
        categdialog = Dialog(this)
        categalertdialog = AlertDialog.Builder(this)
        val itemcategDataBinding: ItemStorecategoryRecycleviewBinding = DataBindingUtil.inflate(
            LayoutInflater.from(categdialog.context),
            R.layout.item_storecategory_recycleview,
            null,
            false
        )
        categView=itemcategDataBinding.root
        itemcategDataBinding.itemViewModel=categoryListViewModel
        categalertdialog.setView(categView)
        categdialog = categalertdialog.create()
        categrv = categView.findViewById(R.id.categRecycler)

        categrv.apply {
            categrv.setHasFixedSize(true)
            categrv.layoutManager = LinearLayoutManager(this@GetMyStoreListedActivity, LinearLayoutManager.VERTICAL ,false)
        }

        nameET.addTextChangedListener(
            MyTextWatcher(
                nameET,
                nameTIL,
                this
            )
        )

        businessnameET.addTextChangedListener(
            MyTextWatcher(
                businessnameET,
                businessnameTIL,
                this
            )
        )

        businessaddressET.addTextChangedListener(
            MyTextWatcher(
                businessaddressET,
                businessaddressTIL,
                this
            )
        )

        mobilenoET.addTextChangedListener(
            MyTextWatcher(
                mobilenoET,
                mobileTIL,
                this
            )
        )

        emailidET.addTextChangedListener(
            MyTextWatcher(
                emailidET,
                emailTIL,
                this
            )
        )

    }

    private fun submit()
    {
        personname = nameET.text.toString()
        businessname = businessnameET.text.toString()
        businessaddress = businessaddressET.text.toString()
        mobile = mobilenoET.text.toString()
        emailid = emailidET.text.toString()

        if (personname.isEmpty()) {
            nameTIL.visibility = View.VISIBLE
            Utils.validateEditText("name", nameET, nameTIL, this)
        } else if (businessname.isEmpty()) {
            businessnameTIL.visibility = View.VISIBLE
            Utils.validateEditText("businessname", businessnameET, businessnameTIL, this)
        } else if (businessaddress.isEmpty()) {
            businessaddressTIL.visibility = View.VISIBLE
            Utils.validateEditText("businessaddress", businessaddressET, businessaddressTIL, this)
        }
        else if (categoryValue.isEmpty()) {
            categoryTIL.visibility = View.VISIBLE
            categoryTIL.error="Select category"
        }
        else if (mobile.isEmpty()) {
            mobileTIL.visibility = View.VISIBLE
            Utils.validateEditText("phone", mobilenoET, mobileTIL, this)
        } else if (emailid.isEmpty()) {
            emailTIL.visibility = View.VISIBLE
            Utils.validateEditText("id", emailidET, emailTIL, this)
        } else {
            Utils.hideKeyBord(this)
            progressbarLL.visibility = View.VISIBLE

            registerShop()
        }

    }

    private fun registerShop()
    {
        getMyStoreListedViewModel.getMyStoreListedResponseLiveData.removeObservers(this)
        getMyStoreListedViewModel.getObserveData().observe(this) {

            if (it?.status == true && it.status_code==201) {
                progressbarLL.visibility = View.GONE
                nameET.setText(" ")
                businessnameET.setText(" ")
                businessaddressET.setText(" ")
                categorytypeTV.setText(" ")
                mobilenoET.setText(" ")
                emailidET.setText(" ")
                nameET.requestFocus()
                Utils.hideKeyBord(this)

                OpenDialogBox.openDialog(
                    this,
                    "Success",
                    "Your store has been listed successfully."
                ,"getmystorelisted"
                )
            } else {
                progressbarLL.visibility = View.GONE
            }
        }
        getMyStoreListedViewModel.getMyStoreResp(
            this, personname, businessname, businessaddress,
            categoryID.toString(), emailid, mobile, "1"
        )
    }

    private fun fetchStoreCategory()
    {
        categoryListViewModel.storeCategoryListLiveData.removeObservers(this)
        categoryListViewModel.getCategoryListObserver()
            .observe(this, Observer<StoreCategoryListResponse> {
                if (it != null) {
                    if (this.lifecycle.currentState == Lifecycle.State.RESUMED) {
                        val categorylist: ArrayList<StoreCategoryData>? = it.data.category
                        if (categorylist != null) {
                            categoryListViewModel.setMyStoreAdapter(categorylist,this)
                        } else {
                            Utils.showToast("Something wrong with api", this)
                        }
                    }
                }
            })

        categoryListViewModel.getCategoryList(this)
    }


    override fun onResume() {
        super.onResume()
        fetchStoreCategory()
    }

    override fun getStoreCategory(value: String, id: Int)
    {
        categdialog.dismiss()
        categoryTIL.isErrorEnabled=false
        categoryTIL.visibility=View.VISIBLE
        categorytypeTV.setText(value)
        categoryValue=value
        categoryID=id
    }

    fun runThread()
    {
        object : Thread()
        {
            @SuppressLint("NotifyDataSetChanged")
            override fun run() {
                try
                {
                finish()
                }
                catch (e: InterruptedException)
                {
                    e.printStackTrace()
                }
            }
        }.start()
    }
}