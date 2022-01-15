package com.app.shopin.HomePage.view.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.shopin.R
import com.app.shopin.UserAuth.view.EditProfileActivity
import com.app.shopin.UserAuth.viewmodel.LoadProfileViewModel
import com.app.shopin.databinding.FragmentMoreBinding
import com.app.shopin.utils.Constant
import com.app.shopin.utils.Preference
import kotlinx.android.synthetic.main.fragment_more.*
import kotlinx.android.synthetic.main.fragment_more.editProfileIV




class MoreFragment : Fragment(),View.OnClickListener
{
    lateinit var binding: FragmentMoreBinding
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

        if (Preference.getInstance(requireActivity())?.getBoolean(Constant.IS_LOGIN)!!)
        {
            logoutBTN.visibility=View.VISIBLE
            loginBTN.visibility=View.GONE
            afterloginLL.visibility=View.VISIBLE
            editProfileIV.setOnClickListener(this)

        }
        else
        {
            logoutBTN.visibility=View.GONE
            loginBTN.visibility=View.VISIBLE
            afterloginLL.visibility=View.GONE
        }


        loadProfile()
    }




    override fun onClick(v: View?) {

        when(v?.id)
        {
            R.id.editProfileIV -> {
                val in7 = Intent(requireActivity(), EditProfileActivity::class.java)
                startActivity(in7)
            }
        }
    }

    override fun onPause() {
        Log.e("onPause","onPause")
        super.onPause()
    }

    override fun onResume() {
        Log.e("onResume","onResume")
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
    }




}