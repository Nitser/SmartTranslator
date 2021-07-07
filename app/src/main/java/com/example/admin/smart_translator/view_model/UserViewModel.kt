package com.example.admin.smart_translator.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.smart_translator.model.PhotoCard
import com.example.admin.smart_translator.utils.PhotoUtils
import com.example.admin.smart_translator.utils.SQLiteDatabaseUtils
import java.io.File
import java.util.ArrayList

class UserViewModel : ViewModel() {

    private val historyPhotoCards = MutableLiveData<ArrayList<PhotoCard>>()
    private val favoritePhotoCards = MutableLiveData<ArrayList<PhotoCard>>()

    fun getHistoryPhotoCards(): LiveData<ArrayList<PhotoCard>> {
        return historyPhotoCards
    }

    fun addHistoryPhotoCards(photoCard: PhotoCard) {
        historyPhotoCards.value?.add(photoCard)
    }

    fun getFavoritePhotoCards(): LiveData<ArrayList<PhotoCard>> {
        return favoritePhotoCards
    }

    fun loadPhotoCards(context: Context) {
        val db = SQLiteDatabaseUtils(context)
        val photoCardsFromDB = db.downloadPhotoCard()
        val storagePhotoService = PhotoUtils()

        photoCardsFromDB.filter { File(it.photoFilePathFromStorage).exists() }
        photoCardsFromDB.map { it.photoBitmap = storagePhotoService.decoding(it.photoFilePathFromStorage) }
        historyPhotoCards.postValue(photoCardsFromDB)

        photoCardsFromDB.filter { it.isLiked }
        favoritePhotoCards.postValue(photoCardsFromDB)
    }

    fun savePhotoCardToStorage(context: Context, photoCard: PhotoCard) {
        val db = SQLiteDatabaseUtils(context)
        db.updatePhotoCard(photoCard)
    }

}