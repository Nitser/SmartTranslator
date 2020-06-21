package com.example.admin.smart_translator.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.ui.photo_card_list.PhotoCardType
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navigationView: BottomNavigationView

    companion object {

        const val clarifaiPath = ""
        const val yandexPath = ""
        const val photoCardBundleTag = "photoCardType"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment_home)
        NavigationUI.setupActionBarWithNavController(this, navController)

        navigationView = findViewById(R.id.bottom_navigation_home)
        navigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                findNavController(R.id.nav_host_fragment_home).navigate(R.id.homeFragment)
                item.isChecked = true
            }
            R.id.navigation_history -> {
                val bundle = Bundle()
                bundle.putSerializable(photoCardBundleTag, PhotoCardType.HistoryList)
                findNavController(R.id.nav_host_fragment_home).navigate(R.id.photoCardFragmentHistory, bundle)
                item.isChecked = true
            }
            R.id.navigation_favorite -> {
                val bundle = Bundle()
                bundle.putSerializable(photoCardBundleTag, PhotoCardType.FavoriteList)
                findNavController(R.id.nav_host_fragment_home).navigate(R.id.photoCardFragmentHistory, bundle)
                item.isChecked = true
            }
        }
        false
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment_home).navigateUp()

}