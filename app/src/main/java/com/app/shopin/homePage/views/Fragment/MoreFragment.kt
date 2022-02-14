package com.app.shopin.homePage.views.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.shopin.R
import com.app.shopin.UserAuth.view.EditProfileActivity
import com.app.shopin.UserAuth.view.EmailRegisterActivity
import com.app.shopin.UserAuth.view.WelcomeToShop
import com.app.shopin.UserAuth.viewmodel.LoadProfileViewModel
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.FragmentMoreBinding
import com.app.shopin.homePage.views.Activity.DeliveryAddressListActivity
import com.app.shopin.homePage.views.Activity.GetMyStoreListedActivity
import com.app.shopin.homePage.views.Activity.HomeActivity
import com.app.shopin.utils.Constant
import com.app.shopin.utils.OpenDialogBox
import com.app.shopin.utils.Preference
import kotlinx.android.synthetic.main.fragment_more.*
import kotlinx.android.synthetic.main.fragment_more.editProfileIV
import kotlinx.android.synthetic.main.fragment_more.profilepicIV

class MoreFragment : Fragment(),View.OnClickListener
{
    lateinit var binding: FragmentMoreBinding
    private lateinit var ctx: HomeActivity
    var isLogin:Boolean=false
    private lateinit var loadProfileViewModel: LoadProfileViewModel
    companion object {
        @JvmStatic
        fun newInstance() =
            MoreFragment().apply {
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
            R.layout.fragment_more,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
    }

    fun initialize()
    {
        loadProfileViewModel = ViewModelProvider(this).get(LoadProfileViewModel::class.java)
        isLogin=Preference.getInstance(requireActivity())?.getBoolean(Constant.IS_LOGIN)!!
        if (isLogin)
        {
            logoutBTN.visibility=View.VISIBLE
            loginBTN.visibility=View.GONE
            afterloginLL.visibility=View.VISIBLE
            editProfileIV.visibility=View.VISIBLE

        }
        else
        {
            logoutBTN.visibility=View.GONE
            loginBTN.visibility=View.VISIBLE
            afterloginLL.visibility=View.GONE
            editProfileIV.visibility=View.GONE
        }
        editProfileIV.setOnClickListener(this)
        logoutBTN.setOnClickListener(this)
        deliveryaddressLL.setOnClickListener(this)
        loginBTN.setOnClickListener(this)
        getmystorelistedLL.setOnClickListener(this)
        loadProfile()
    }




    override fun onClick(v: View?) {

        when(v?.id)
        {
            R.id.editProfileIV -> {
                if (isLogin)
                {
                    val in7 = Intent(requireActivity(), EditProfileActivity::class.java)
                    startActivity(in7)
                }
                else
                {
                    ctx.openBottomSheetDialog(requireContext())
                }


            }

            R.id.deliveryaddressLL ->
            {
                if (isLogin)
                {
                    val in7 = Intent(requireActivity(), DeliveryAddressListActivity::class.java)
                    startActivity(in7)
                }
                else
                {
                    ctx.openBottomSheetDialog(requireContext())
                }


            }


            R.id.logoutBTN -> {
                OpenDialogBox.openLogoutDialog(requireActivity())
            }

            R.id.getmystorelistedLL -> {
                if (isLogin)
                {
                    val intent = Intent(requireActivity(), GetMyStoreListedActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    ctx.openBottomSheetDialog(requireContext())
                }

            }

            R.id.loginBTN -> {
                val intent = Intent(requireActivity(), EmailRegisterActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        loadProfile()
    }



    fun loadProfile()
    {
        if (!Preference.getInstance(requireActivity())!!.getString(Constant.KEY_NAME).isEmpty())
        {
            nametv.visibility=View.VISIBLE
            nametv.text=Preference.getInstance(requireActivity())!!.getString(Constant.KEY_NAME)

        }
        else
        {
            nametv.visibility=View.GONE

        }
        if (!Preference.getInstance(requireActivity())!!.getString(Constant.KEY_EMAIL_ID).isEmpty())
        {
            emailidTV.visibility=View.VISIBLE
            emailidTV.text=Preference.getInstance(requireActivity())!!.getString(Constant.KEY_EMAIL_ID)

        }
        else
        {
            emailidTV.visibility=View.GONE

        }
        if (!Preference.getInstance(requireActivity())!!.getString(Constant.KEY_USER_ID).isEmpty())
        {
            useridTV.visibility=View.VISIBLE
            useridTV.text="User ID- "+Preference.getInstance(requireActivity())!!.getString(Constant.KEY_USER_ID)

        }
        else
        {
            useridTV.visibility=View.GONE

        }
        if (!Preference.getInstance(requireActivity())!!.getString(Constant.KEY_USER_PIC).isEmpty())
        {
            val profilepic=Preference.getInstance(requireActivity())!!.getString(Constant.KEY_USER_PIC)
            Utils.setImage(profilepicIV, profilepic, R.drawable.defult_user)

        }

    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        ctx = context as HomeActivity

    }


}