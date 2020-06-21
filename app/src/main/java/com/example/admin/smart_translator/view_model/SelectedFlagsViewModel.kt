package com.example.admin.smart_translator.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.smart_translator.entities.Language
import com.example.admin.smart_translator.entities.LanguageEnum
import java.util.ArrayList

class SelectedFlagsViewModel : ViewModel() {

    private val selectedFlags = MutableLiveData<ArrayList<Language>>()

    fun getSelectedFlags(): LiveData<ArrayList<Language>> {
        return selectedFlags
    }

    fun setSelectedFlags(flags: ArrayList<Language>) {
        selectedFlags.postValue(flags)
    }

    fun setDefaultFlags(allFlagList : ArrayList<Language>) {
        val defaultArray = ArrayList<Language>()
        defaultArray.add(allFlagList.find { it.langCode == LanguageEnum.ENGLISH }!!)
        defaultArray.add(allFlagList.find { it.langCode == LanguageEnum.RUSSIAN }!!)
        selectedFlags.postValue(defaultArray)
    }

}