package com.shai.autodesk

import android.R
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

abstract class BaseActivity : AppCompatActivity() {

    fun replaceFragment(@IdRes container: Int, fragment: Fragment?,
                                tag: String): FragmentTransaction {
        return supportFragmentManager
            .beginTransaction()
            .replace(container, fragment!!, tag)
            .addToBackStack(tag)
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
    }

    fun addFragment(@IdRes container: Int, fragment: Fragment?,
                            tag: String): FragmentTransaction {
        return supportFragmentManager
            .beginTransaction()
            .add(container, fragment!!, tag)
            .addToBackStack(tag)
            .setCustomAnimations(R.anim.fade_in, R.anim.fade_out)
    }

    override fun onBackPressed() {
        val fm: FragmentManager = supportFragmentManager
        if (fm.backStackEntryCount > 1) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(false)
            fm.popBackStack()
        } else {
            finish()
        }
    }
}