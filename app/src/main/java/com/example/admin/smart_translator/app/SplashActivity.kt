package com.example.admin.smart_translator.app

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import android.view.Window
import android.widget.TextView

import com.example.admin.smart_translator.Models.PhotoCard
import com.example.admin.smart_translator.Models.User
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.Services.DataBaseService
import com.example.admin.smart_translator.Services.PhotoService

import java.io.File

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.splash_layout)

        init()

        Handler().postDelayed({
            loadPhotoCard()
            val intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }, (3 * 1000).toLong())
    }

    private fun init() {
        PhotoCard.width = windowManager.defaultDisplay.width

        val label_1 = findViewById<TextView>(R.id.label_1)
        val label_2 = findViewById<TextView>(R.id.label_2)

        val custom_font_label_1 = Typeface.createFromAsset(assets, "fonts/haetten.ttf")
        val custom_font_label_2 = Typeface.createFromAsset(assets, "fonts/ITCEDSCR.TTF")

        label_1.typeface = custom_font_label_1
        label_2.typeface = custom_font_label_2
    }

    private fun loadPhotoCard() {
        val db = DataBaseService(this)
        db.loadPhotoCard()
        val storagePhotoService = PhotoService()
        for (photoCard in User.photoCardStorage) {
            val file = File(photoCard.filePath)
            if (file.exists())
                photoCard.bitmap = storagePhotoService.decodingPhoto(photoCard.filePath, PhotoCard.width, PhotoCard.width)
            else {
                db.deletePhotoCard(photoCard)
                User.photoCardStorage.remove(photoCard)
                User.likedPhotoCardStorage.remove(photoCard)
            }
        }
    }
}
