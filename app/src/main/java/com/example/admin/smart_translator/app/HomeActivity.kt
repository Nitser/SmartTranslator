package com.example.admin.smart_translator.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.example.admin.smart_translator.Fragments.CoffeeFragment
import com.example.admin.smart_translator.Fragments.FavoriteFragment
import com.example.admin.smart_translator.Fragments.HistoryFragment
import com.example.admin.smart_translator.Fragments.HomeFragment
import com.example.admin.smart_translator.R

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
