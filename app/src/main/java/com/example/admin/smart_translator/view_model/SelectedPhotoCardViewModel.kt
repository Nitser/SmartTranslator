package com.example.admin.smart_translator.view_model

import android.content.Context
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.smart_translator.model.PhotoCard
import com.example.admin.smart_translator.utils.FileStorageUtils
import com.example.admin.smart_translator.utils.PhotoUtils

class SelectedPhotoCardViewModel : ViewModel() {

    private val photoUtils = PhotoUtils()
    private val fileStorageUtils = FileStorageUtils()
    private val selectedPhotoCard = MutableLiveData<PhotoCard>()

    fun getSelectedPhotoCard(): LiveData<PhotoCard> {
        return selectedPhotoCard
    }

    fun setSelectedPhotoCard(photoCard: PhotoCard) {
        selectedPhotoCard.value = photoCard
    }

    fun setSelectedPhotoCardFromStorage(photoCard: PhotoCard): PhotoCard {
        val myBitmap = photoUtils.decoding(photoCard.photoFilePathFromStorage)
        photoUtils.compression(myBitmap, photoCard.photoFilePathFromStorage)
        photoCard.photoBitmap = myBitmap
        selectedPhotoCard.postValue(photoCard)
        return photoCard
    }

    fun getPhotoFromGallery(context: Context, photoCard: PhotoCard, uri: Uri): PhotoCard {
        val photoFile = fileStorageUtils.createImageFile(context)
        photoCard.photoFilePathFromStorage = photoFile.absolutePath

        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, uri)
            ImageDecoder.decodeBitmap(source)
        } else {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }

        photoUtils.compression(bitmap, photoCard.photoFilePathFromStorage)
        photoUtils.decoding(photoCard.photoFilePathFromStorage)
        photoCard.photoBitmap = bitmap
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