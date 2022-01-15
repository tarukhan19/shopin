package com.app.shopin.UserAuth.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.app.shopin.R
import com.app.shopin.UserAuth.model.EditProfileResponse
import com.app.shopin.UserAuth.viewmodel.LoadProfileViewModel
import com.app.shopin.Util.Utils
import com.app.shopin.databinding.ActivityEditProfileBinding
import com.app.shopin.utils.Constant
import com.app.shopin.utils.FileUtils
import com.app.shopin.utils.Preference
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.editProfileIV
import kotlinx.android.synthetic.main.activity_edit_profile.mobilenoET
import kotlinx.android.synthetic.main.activity_edit_profile.profilepicIV
import kotlinx.android.synthetic.main.activity_edit_profile.progressbarLL
import kotlinx.android.synthetic.main.activity_mobile_register.*
import kotlinx.android.synthetic.main.fragment_more.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class EditProfileActivity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding: ActivityEditProfileBinding
    var name: String = ""
    var emailid: String = ""
    var mobileno: String = ""
    var profilepic: String=""
    var imageuri: Uri? =null
    private lateinit var loadProfileViewModel: LoadProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_profile)
        initialize()
    }






    fun initialize()
    {
        loadProfileViewModel = ViewModelProvider(this).get(LoadProfileViewModel::class.java)
        profilepicIV.setOnClickListener(this)
        editProfileIV.setOnClickListener(this)
        saveBTN.setOnClickListener(this)
        loadProfile()
    }

    fun loadProfile()
    {
        progressbarLL.visibility = View.VISIBLE
        loadProfileViewModel.getObserve().observe(this, {
            if (it?.status==true  && it.status_code==200) {
                progressbarLL.visibility = View.GONE

                if (it.profileResponseItems.user_profile.email!=null)
                {
                    emailid=it.profileResponseItems.user_profile.email
                    Preference.getInstance(this)?.setString(Constant.KEY_EMAIL_ID,emailid)
                    emailidET.setText(emailid)

                }
                if (it.profileResponseItems.user_profile.phone_no!=null)
                {
                    mobileno=it.profileResponseItems.user_profile.phone_no
                    Preference.getInstance(this)?.setString(Constant.KEY_MOBILE_NO,mobileno)
                    mobilenoET.setText(mobileno)

                }
                if (it.profileResponseItems.user_profile.name!=null)
                {
                    name=it.profileResponseItems.user_profile.name
                    Preference.getInstance(this)?.setString(Constant.KEY_NAME,name)
                    nameET.setText(name)
                }

                if (it.profileResponseItems.user_profile.profile_img!=null)
                {
                    profilepic="https://shopinzip.cladev.com"+it.profileResponseItems.user_profile.profile_img
                    Preference.getInstance(this)?.setString(Constant.KEY_USER_PIC,profilepic)
                    try {
                        Picasso.get().load(profilepic).placeholder(R.drawable.defult_user).into(profilepicIV)
                    }
                    catch ( e: Exception)
                    {
                        //Log.e("exception",e.localizedMessage)
                    }
                }

            }
            else
            {
                progressbarLL.visibility = View.GONE
                Utils.showToast("Something Went wrong",this)
            }
        })
        loadProfileViewModel.makeLoadProfileApiCall(this)
    }

    override fun onClick(v: View?) {
        when (v?.id)
        {
            R.id.profilepicIV -> {
                openSomeActivityForResult()
            }
            R.id.editProfileIV -> {
                openSomeActivityForResult()
            }
            R.id.saveBTN ->
            {
                submit()
            }

        }
    }

    fun submit()
    {
        name=nameET.text.toString()
        mobileno=mobilenoET.text.toString()
        emailid=emailidET.text.toString()

        Log.e("name",name)
        Log.e("mobileno",mobileno)
        Log.e("emailid",emailid)

        updateProfile()
    }

    fun updateProfile()
    {
        progressbarLL.visibility = View.VISIBLE
        val rbEmail = emailid.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val rbName = name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val rbMobile = mobileno.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        var profilepicmultipartimage: MultipartBody.Part? = null

        if (imageuri != null)
        {
            profilepicmultipartimage=prepareFilePart("profile_img", imageuri!!)
        }
        // Parsing any Media type file
        val getResponse = ServiceBuilder.getApiService(this)
        val call = getResponse.editProfile(rbEmail, rbName, rbMobile,profilepicmultipartimage)
        call.enqueue(object : Callback<EditProfileResponse> {
            override fun onResponse(
                call: Call<EditProfileResponse>,
                response: Response<EditProfileResponse>
            ) {
                Toast.makeText(
                    this@EditProfileActivity,
                    response.isSuccessful.toString(),
                    Toast.LENGTH_SHORT
                ).show()

                if (response.isSuccessful) {
                    binding.progressbarLL.setVisibility(View.GONE)

                    if (response.body() != null) {
                        val serverResponse = response.body()
                        Toast.makeText(
                            this@EditProfileActivity,
                            serverResponse?.status_code.toString(),
                            Toast.LENGTH_SHORT
                        ).show()


                        binding.nameET.requestFocus()
                        loadProfile()
                    }
                } else {
                    val serverResponse = response.body()
                    Toast.makeText(
                        this@EditProfileActivity,
                        serverResponse?.status.toString(),
                        Toast.LENGTH_SHORT
                    ).show()

                }
            }

            override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
                Utils.printLog(t.message+"  "+t.localizedMessage,"failuree ")
                Toast.makeText(
                    this@EditProfileActivity,
                    "failure "+t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }


    private fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part
    {
        val file: File = FileUtils.getFile(this, fileUri)
        val requestFile = file.asRequestBody(FileUtils.MIME_TYPE_IMAGE.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }




    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK)
        {
            imageuri= result?.data?.data!!
            profilepicIV.setImageURI(imageuri) // handle chosen image

        }
    }

    fun openSomeActivityForResult() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}