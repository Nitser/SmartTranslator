package com.example.admin.smarttranslator.Activities

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.admin.smarttranslator.Fragments.CoffeeFragment
import com.example.admin.smarttranslator.Fragments.FavoriteFragment
import com.example.admin.smarttranslator.Fragments.HistoryFragment
import com.example.admin.smarttranslator.Fragments.HomeFragment
import com.example.admin.smarttranslator.R

class HomeActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = { item ->

        when (item.getItemId()) {
            R.id.navigation_home -> {
                loadFragment(HomeFragment.newInstance())
                return true
            }
            R.id.navigation_dashboard -> {
                loadFragment(FavoriteFragment.newInstance())
                return true
            }
            R.id.navigation_notifications -> {
                loadFragment(HistoryFragment.newInstance())
                return true
            }
            R.id.navigation_person -> {
                loadFragment(CoffeeFragment.newInstance())
                return true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_layout)

        val navigation = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (intent.hasExtra("Fragment")) {
            when (intent.getStringExtra("Fragment")) {
                "home" -> navigation.selectedItemId = R.id.navigation_home
                "history" -> navigation.selectedItemId = R.id.navigation_notifications
                "favorite" -> navigation.selectedItemId = R.id.navigation_dashboard
            }
        } else {
            navigation.selectedItemId = R.id.navigation_home
        }

    }

    private fun loadFragment(fragment: androidx.fragment.app.Fragment) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fl_content, fragment)
        ft.commit()
    }

}
