package com.example.admin.smart_translator.view_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.smart_translator.entities.PhotoCard
import com.example.admin.smart_translator.model.DataBaseService
import com.example.admin.smart_translator.model.PhotoService
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
        // Базу данных обычно делают через синглтон и не создают при каждом обращении к ней. Могут появиться проблемы с несколькими соединениями к бд.
        val db = DataBaseService(context)
        val photoCardsFromDB = db.loadPhotoCard()
        val storagePhotoService = PhotoService()

        // Методы типа filter и map не изменяют коллекцию, а возвращают новую.
        // То есть (если я не ошибаюсь) тут происходит работа впустую
        // и сюда historyPhotoCards.postValue(photoCardsFromDB) передаётся photoCardsFromDB без изменений
        photoCardsFromDB.filter { File(it.filePath).exists() }
        photoCardsFromDB.map { it.bitmap = storagePhotoService.decodingPhoto(it.filePath) }
        historyPhotoCards.postValue(photoCardsFromDB)

        photoCardsFromDB.filter { it.isLiked }
        favoritePhotoCards.postValue(photoCardsFromDB)
    }

    fun savePhotoCardToStorage(context: Context, photoCard: PhotoCard) {
        val db = DataBaseService(context)
        db.addPhotoCard(photoCard)
    }

}