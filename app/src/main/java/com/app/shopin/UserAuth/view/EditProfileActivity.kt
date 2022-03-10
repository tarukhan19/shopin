
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
import com.app.shopin.homePage.models.ErrorResponse
import com.app.shopin.utils.Constant
import com.app.shopin.utils.FileUtils
import com.app.shopin.utils.OpenDialogBox
import com.app.shopin.utils.Preference
import com.customer.gogetme.Retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_edit_profile.editProfileIV
import kotlinx.android.synthetic.main.activity_edit_profile.mobilenoET
import kotlinx.android.synthetic.main.activity_edit_profile.profilepicIV
import kotlinx.android.synthetic.main.activity_edit_profile.progressbarLL
import kotlinx.android.synthetic.main.activity_mobile_register.*
import kotlinx.android.synthetic.main.fragment_more.*
import kotlinx.android.synthetic.main.toolbar.view.*
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
        toolbar.titleTV.setText(R.string.editaddress)
        toolbar.back_LL.setOnClickListener(this)
        loadProfile()
    }

    fun loadProfile()
    {
        progressbarLL.visibility = View.VISIBLE
        loadProfileViewModel.getObserve().observe(this) {
            if (it?.status == true && it.status_code == 200) {
                progressbarLL.visibility = View.GONE

                if (it.profileResponseItems.user_profile.email != null) {
                    emailid = it.profileResponseItems.user_profile.email
                    Preference.getInstance(this)?.setString(Constant.KEY_EMAIL_ID, emailid)
                    emailidET.setText(emailid)

                }
                if (it.profileResponseItems.user_profile.phone_no != null) {
                    mobileno = it.profileResponseItems.user_profile.phone_no
                    Preference.getInstance(this)?.setString(Constant.KEY_MOBILE_NO, mobileno)
                    mobilenoET.setText(mobileno)

                }
                if (it.profileResponseItems.user_profile.name != null) {
                    name = it.profileResponseItems.user_profile.name
                    Preference.getInstance(this)?.setString(Constant.KEY_NAME, name)
                    nameET.setText(name)
                }

                if (it.profileResponseItems.user_profile.profile_img != null) {
                    profilepic =
                        "https://shopinzip.cladev.com" + it.profileResponseItems.user_profile.profile_img
                    Preference.getInstance(this)?.setString(Constant.KEY_USER_PIC, profilepic)
                    Utils.setImage(profilepicIV, profilepic, R.drawable.defult_user)

                }


            } else {
                progressbarLL.visibility = View.GONE
                Utils.showToast("Something Went wrong", this)
            }
        }
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
            R.id.back_LL ->
            {
                finish()
            }

        }
    }

    fun submit()
    {
        name=nameET.text.toString()
        mobileno=mobilenoET.text.toString()
        emailid=emailidET.text.toString()


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
        val getResponse = ServiceBuilder.getApiService(this)
        val call = getResponse.editProfile(rbEmail, rbName, rbMobile,profilepicmultipartimage)
        call.enqueue(object : Callback<EditProfileResponse> {
            override fun onResponse(
                call: Call<EditProfileResponse>,
                response: Response<EditProfileResponse>
            ) {

                if (response.isSuccessful) {
                    binding.progressbarLL.setVisibility(View.GONE)

                    if (response.body() != null) {
                        val serverResponse = response.body()

                        binding.nameET.requestFocus()
                        loadProfile()
                        OpenDialogBox.openDialog(this@EditProfileActivity,"Success","Your profile has been updated successfully.")

                    }
                } else {
                    val gson = Gson()
                    val type = object : TypeToken<ErrorResponse>() {}.type
                    val errorResponse: ErrorResponse? = gson.fromJson(response.errorBody()!!.charStream(), type)
                    Utils.showToast(errorResponse!!.msg,this@EditProfileActivity)
                }
            }

            override fun onFailure(call: Call<EditProfileResponse>, t: Throwable) {
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