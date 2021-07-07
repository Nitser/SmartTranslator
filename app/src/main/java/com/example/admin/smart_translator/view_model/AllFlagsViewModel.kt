package com.example.admin.smart_translator.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.admin.smart_translator.model.Flag
import java.util.ArrayList

class AllFlagsViewModel : ViewModel() {

    private val allFlags = MutableLiveData<ArrayList<Flag>>()

    fun getAllFlags(): LiveData<ArrayList<Flag>> {
        return allFlags
    }

    fun setAllFlags(flags: ArrayList<Flag>) {
        allFlags.postValue(flags)
    }

}