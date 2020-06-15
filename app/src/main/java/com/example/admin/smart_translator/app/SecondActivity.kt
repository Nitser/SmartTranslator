package com.example.admin.smart_translator.app

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast

import com.example.admin.smart_translator.Adapters.ResultListAdapter
import com.example.admin.smart_translator.Api.ClarifaiApi
import com.example.admin.smart_translator.Api.YandexTranslatorApi
import com.example.admin.smart_translator.Models.PhotoCard
import com.example.admin.smart_translator.Models.User
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.Services.DataBaseService
import com.example.admin.smart_translator.Services.FileStorageService
import com.example.admin.smart_translator.Services.PhotoService

import java.io.File
import java.io.IOException

class SecondActivity : AppCompatActivity() {

    var recyclerView: androidx.recyclerview.widget.RecyclerView? = null
        private set
    private var mImageView: ImageView? = null
    private var checkBox: CheckBox? = null
    private var width: Int = 0

    private var previous: String? = null
    private var currentPhotoCard: PhotoCard? = null
    private val lastUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        when (intent.getStringExtra("PhotoCard_Parent")) {
            "home" -> previous = "home"
            "history" -> previous = "history"
            "favorite" -> previous = "favorite"
        }

        if (intent.hasExtra("PhotoCard_Position")) {
            status = 2
            if (previous == "history")
                currentPhotoCard = User.photoCardStorage[intent.getIntExtra("PhotoCard_Position", 0)]
            else
                currentPhotoCard = User.likedPhotoCardStorage[intent.getIntExtra("PhotoCard_Position", 0)]
            init()
            mImageView!!.setImageBitmap(currentPhotoCard!!.bitmap)
            val adapter = ResultListAdapter(currentPhotoCard!!.resultFrom, currentPhotoCard!!.resultTo, width)
            recyclerView!!.adapter = adapter
            if (currentPhotoCard!!.isLiked)
                checkBox!!.isChecked = true
        } else {
            status = 0
            currentPhotoCard = User.photoCardStorage[User.photoCardStorage.size - 1]
            init()
            if (intent.getStringExtra("Option") == "Photo")
                startDispatchTakePictureIntent()
            else
                startDispatchGalleryIntent()
        }

    }

    private fun init() {
        val from = findViewById<ImageView>(R.id.fromFlag)
        val to = findViewById<ImageView>(R.id.toFlag)
        val fromId = resources.getIdentifier("ic_" + currentPhotoCard!!.lanFrom!!, "mipmap", packageName)
        val toId = resources.getIdentifier("ic_" + currentPhotoCard!!.lanTo!!, "mipmap", packageName)

        from.setBackgroundResource(fromId)
        to.setBackgroundResource(toId)

        mImageView = findViewById(R.id.ivPhoto)
        width = windowManager.defaultDisplay.width
        mImageView!!.layoutParams.height = width
        checkBox = findViewById(R.id.heart)
        checkBox!!.setOnClickListener { view ->
            currentPhotoCard!!.changeLike()
            if (currentPhotoCard!!.isLiked)
                User.likedPhotoCardStorage.add(currentPhotoCard)
            else
                User.likedPhotoCardStorage.remove(currentPhotoCard)
        }

        recyclerView = findViewById(R.id.list_results)
        recyclerView!!.setHasFixedSize(true)
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager

        photoService = PhotoService()
        fileStorageService = FileStorageService()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        if (requestCode == 1) {
            if (intent == null) {
                try {
                    val myBitmap = photoService!!.decodingPhoto(currentPhotoCard!!.filePath, width, width)
                    photoService!!.compressPhoto(myBitmap, currentPhotoCard!!.filePath)
                    mImageView!!.setImageBitmap(myBitmap)
                    currentPhotoCard!!.bitmap = myBitmap
                    status = 1
                    onClickCheck()
                } catch (e: Exception) {
                    Toast.makeText(this, "Problem", Toast.LENGTH_LONG).show()
                    Log.e("!!!!!!!!!!!_E", e.toString())
                }

            }
        } else if (requestCode == 4 && resultCode == Activity.RESULT_OK && intent != null && intent.data != null) {
            val uri = intent.data
            try {
                var photoFile: File? = null
                try {
                    photoFile = fileStorageService!!.createImageFile(this)
                } catch (ex: IOException) {
                    Log.e("!!!!!!!!!!!_IO", ex.toString())
                }

                if (photoFile != null)
                    currentPhotoCard!!.filePath = photoFile.absolutePath

                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                photoService!!.compressPhoto(bitmap, currentPhotoCard!!.filePath)
                photoService!!.decodingPhoto(currentPhotoCard!!.filePath, width, width)
                mImageView!!.setImageBitmap(bitmap)
                currentPhotoCard!!.bitmap = bitmap
                status = 1
                onClickCheck()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        } else if (requestCode == 3) {
            val from = findViewById<ImageView>(R.id.fromFlag)
            val to = findViewById<ImageView>(R.id.toFlag)

            currentPhotoCard!!.lanFrom = intent!!.getStringExtra("from")
            currentPhotoCard!!.lanTo = intent.getStringExtra("to")

            val fromId = resources.getIdentifier("ic_" + currentPhotoCard!!.lanFrom!!, "mipmap", packageName)
            val toId = resources.getIdentifier("ic_" + currentPhotoCard!!.lanTo!!, "mipmap", packageName)

            from.setBackgroundResource(fromId)
            to.setBackgroundResource(toId)

            if (currentPhotoCard!!.resultFrom.size != 0) {
                currentPhotoCard!!.resultFrom.clear()
                currentPhotoCard!!.resultTo.clear()
                recyclerView!!.adapter!!.notifyDataSetChanged()
            }

            val translatorApi = YandexTranslatorApi(currentPhotoCard, currentPhotoCard!!.neoResult) {
                val adapter = ResultListAdapter(currentPhotoCard!!.resultFrom, currentPhotoCard!!.resultTo, width)
                recyclerView!!.adapter = adapter
            }
            translatorApi.execute(currentPhotoCard!!.lanFrom, currentPhotoCard!!.lanTo)
        }
    }

    override fun onPause() {
        super.onPause()
        if (status == 1) {
            val db = DataBaseService(this)
            db.addPhotoCard(currentPhotoCard)
        } else if (status == 2) {
            val db = DataBaseService(this)
            db.updatePhotoCard(currentPhotoCard)
        }
    }

    private fun startDispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = fileStorageService!!.createImageFile(this)
            } catch (ex: IOException) {
                Log.e("!!!!!!!!!!!_IO", ex.toString())
            }

            if (photoFile != null) {
                currentPhotoCard!!.filePath = photoFile.absolutePath
                val photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile)
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, 1)
            }
        }
    }

    private fun startDispatchGalleryIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 4)
    }

    private fun startCropImageIntent() {
        try {
            val cropIntent = Intent("com.android.camera.action.CROP")
            cropIntent.setDataAndType(lastUri, "image/*")
            cropIntent.putExtra("return-data", true)
            cropIntent.putExtra("output", lastUri)
            cropIntent.putExtra("outputFormat", "JPEG")
            startActivityForResult(cropIntent, 2)
        } catch (cant: ActivityNotFoundException) {
            val errorMessage = "Ваше устройство не поддерживает работу с камерой!"
            val toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
            toast.show()
        }

    }

    fun onClickCheck() {
        val task = ClarifaiApi(Uri.fromFile(File(currentPhotoCard!!.filePath)), currentPhotoCard) {
            val adapter = ResultListAdapter(currentPhotoCard!!.resultFrom, currentPhotoCard!!.resultTo, width)
            recyclerView!!.adapter = adapter
        }
        task.execute()
    }

    fun onClickFlagButton(view: View) {
        val intent = Intent(this@SecondActivity, FlagChangeActivity::class.java)
        startActivityForResult(intent, 3)
    }

    fun onClickBackButton(view: View) {
        val intent = Intent(this@SecondActivity, HomeActivity::class.java)
        intent.putExtra("Fragment", previous)
        startActivity(intent)
    }

    companion object {
        private var photoService: PhotoService? = null
        private var fileStorageService: FileStorageService? = null

        private var status: Int = 0 // 0 - before Camera , 1 - new PhotoCard, 2 - old PhotoCard
    }

}