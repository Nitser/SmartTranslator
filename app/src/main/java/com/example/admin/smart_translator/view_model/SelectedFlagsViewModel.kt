package com.example.admin.smart_translator.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.smart_translator.model.Flag
import com.example.admin.smart_translator.model.LanguageEnum
import java.util.ArrayList

class SelectedFlagsViewModel : ViewModel() {

    private val selectedFlags = MutableLiveData<ArrayList<Flag>>()

    fun getSelectedFlags(): LiveData<ArrayList<Flag>> {
        return selectedFlags
    }

    fun setSelectedFlags(flags: ArrayList<Flag>) {
        selectedFlags.postValue(flags)
    }

    fun setDefaultSelectedFlags(allFlagList: ArrayList<Flag>) {
        val defaultSelectedFlags = ArrayList<Flag>()
        defaultSelectedFlags.add(allFlagList.find { it.langCode == LanguageEnum.ENGLISH }!!)
        defaultSelectedFlags.add(allFlagList.find { it.langCode == LanguageEnum.RUSSIAN }!!)
        selectedFlags.postValue(defaultSelectedFlags)
    }

}