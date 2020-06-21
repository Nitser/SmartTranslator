package com.example.admin.smart_translator.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.smart_translator.entities.Language
import java.util.ArrayList

class FlagsViewModel : ViewModel() {

    private val flags = MutableLiveData<ArrayList<Language>>()

    fun getFlags(): LiveData<ArrayList<Language>> {
        return flags
    }

    fun setFlags(newFlags: ArrayList<Language>) {
        flags.postValue(newFlags)
    }

}