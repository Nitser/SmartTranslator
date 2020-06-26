package com.example.admin.smart_translator.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.admin.smart_translator.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    companion object {
        const val clarifaiApiKey = "c151c5d2a75e4ba1aa32bb937af40643"
        private const val yandexTranslatorApiKey = "trnsl.1.1.20180305T215814Z.f53d4e296c91e95e.5993ed4ff1955da06bcb18ac2eaf3bf8b45cd644"
        const val yandexTranslatorApiURI = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=$yandexTranslatorApiKey"
        const val fragmentType = "photoCardType"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment_home)
        NavigationUI.setupActionBarWithNavController(this, navController)
        findViewById<BottomNavigationView>(R.id.bottom_navigation_home).setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                findNavController(R.id.nav_host_fragment_home).navigate(R.id.homeFragment)
                item.isChecked = true
            }
            R.id.navigation_history -> {
                val bundle = Bundle()
                bundle.putSerializable(fragmentType, PhotoCardType.HistoryList)
                findNavController(R.id.nav_host_fragment_home).navigate(R.id.photoCardFragmentHistory, bundle)
                item.isChecked = true
            }
            R.id.navigation_favorite -> {
                val bundle = Bundle()
                bundle.putSerializable(fragmentType, PhotoCardType.FavoriteList)
                findNavController(R.id.nav_host_fragment_home).navigate(R.id.photoCardFragmentHistory, bundle)
                item.isChecked = true
            }
        }
        false
    }

    override fun onSupportNavigateUp() = findNavController(R.id.nav_host_fragment_home).navigateUp()

}