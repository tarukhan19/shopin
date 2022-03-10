package com.app.shopin.homePage.views.Activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.shopin.homePage.views.Fragment.HomeFragment
import com.app.shopin.homePage.views.Fragment.MoreFragment
import com.app.shopin.homePage.views.Fragment.SearchFragment
import com.app.shopin.homePage.views.Fragment.StoreFragment
import com.app.shopin.R
import com.app.shopin.UserAuth.view.EmailRegisterActivity
import com.app.shopin.databinding.ActivityHomeBinding
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {
    private val ID_HOME = 0
    private val ID_STORE = 1
    private val ID_SEARCH = 2
    private val ID_MORE = 3
    var CURRENT_TAG = ID_HOME


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityHomeBinding>(
            this,
            R.layout.activity_home
        )
        addFragment(HomeFragment.newInstance())
        bottomNavigation.show(0)
        bottomNavigation.add(MeowBottomNavigation.Model(0,R.drawable.home_inactive))
        bottomNavigation.add(MeowBottomNavigation.Model(1,R.drawable.store_inactive))
        bottomNavigation.add(MeowBottomNavigation.Model(2,R.drawable.search_inactive))
        bottomNavigation.add(MeowBottomNavigation.Model(3,R.drawable.profile_inactive))

        bottomNavigation.setOnClickMenuListener {
            when(it.id){
                0 -> {
                    replaceFragment(HomeFragment.newInstance())
                    CURRENT_TAG=ID_HOME
                }
                1 -> {
                    replaceFragment(StoreFragment.newInstance())
                    CURRENT_TAG=ID_STORE

                }
                2 -> {
                    replaceFragment(SearchFragment.newInstance())
                    CURRENT_TAG=ID_SEARCH

                }
                3 -> {
                    replaceFragment(MoreFragment.newInstance())
                    CURRENT_TAG=ID_MORE

                }

                else -> {
                    replaceFragment(HomeFragment.newInstance())
                    CURRENT_TAG=ID_HOME

                }
            }
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }

    private fun addFragment(fragment: Fragment){
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.add(R.id.fragmentContainer,fragment).addToBackStack(Fragment::class.java.simpleName).commit()
    }





    override fun onBackPressed() {
        super.onBackPressed()
        if (CURRENT_TAG == ID_HOME) {
            finish()
        } else {
            CURRENT_TAG = ID_HOME
            replaceFragment(HomeFragment.newInstance())
            bottomNavigation.show(0)
        }
    }



    fun  openBottomSheetDialog(context: Context)
    {
        val dialog = BottomSheetDialog(context)
        val view = layoutInflater.inflate(R.layout.item_bottomsheet_login_alert, null)
        val backbtn = view.findViewById<TextView>(R.id.backbtn)
        val loginBTN = view.findViewById<Button>(R.id.loginBTN)

        backbtn.setOnClickListener { dialog.dismiss() }
        loginBTN.setOnClickListener {
            val intent = Intent(this, EmailRegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }

        dialog.setCancelable(false)
        dialog.setContentView(view)
        dialog.show()

    }

}