package com.example.admin.smart_translator.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.ui.photo_card_list.PhotoCardType
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navigationView: BottomNavigationView

    // companion object в Activity и Fragment обычно не используют, так как в неоторых случаях это может привести к утечками памяти
    // Константы можно вынести или в отдельный файл или написать их перед class MainActivity в виде: private const val clarifaiPath = ""
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

        // Можно испольовать синтетики и сразу писать bottom_navigation_home.some_method.
        // Тогда эти две строки превратились бы в одну
        //   bottom_navigation_home.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        // Или тут есть какое-то преимущество в использовании findViewById?
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