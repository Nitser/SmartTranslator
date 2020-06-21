package com.example.admin.smart_translator.view_model

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.smart_translator.entities.PhotoCard
import com.example.admin.smart_translator.model.FileStorageService
import com.example.admin.smart_translator.model.PhotoService
import java.io.File

class CurrentPhotoCardViewModel : ViewModel() {

    private val photoService = PhotoService()
    private val fileStorageService = FileStorageService()
    private val currentPhotoCards = MutableLiveData<PhotoCard>()

    fun getCurrentPhotoCards(): LiveData<PhotoCard> {
        return currentPhotoCards
    }

    fun setCurrentPhotoCards(photoCard: PhotoCard) {
        currentPhotoCards.value = photoCard
    }

    fun saveNewPhotoToStorage(photoCard: PhotoCard): PhotoCard {
        val myBitmap = photoService.decodingPhoto(photoCard.filePath)
        photoService.compressPhoto(myBitmap, photoCard.filePath)
        photoCard.bitmap = myBitmap
        currentPhotoCards.postValue(photoCard)
        return photoCard
    }

    fun getPhotoFromGallery(context: Context, photoCard: PhotoCard, uri: Uri): PhotoCard {
        val photoFile: File?
        photoFile = fileStorageService.createImageFile(context)
        photoCard.filePath = photoFile.absolutePath

        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }

        photoService.compressPhoto(bitmap, photoCard.filePath)
        photoService.decodingPhoto(photoCard.filePath)
        photoCard.bitmap = bitmap
        return photoCard
    }

//    private fun startCropImageIntent() {
//        try {
//            val cropIntent = Intent("com.android.camera.action.CROP")
//            cropIntent.setDataAndType(lastUri, "image/*")
//            cropIntent.putExtra("return-data", true)
//            cropIntent.putExtra("output", lastUri)
//            cropIntent.putExtra("outputFormat", "JPEG")
//            startActivityForResult(cropIntent, 2)
//        } catch (cant: ActivityNotFoundException) {
//            val errorMessage = "Ваше устройство не поддерживает работу с камерой!"
//            val toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT)
//            toast.show()
//        }
//
//    }

}