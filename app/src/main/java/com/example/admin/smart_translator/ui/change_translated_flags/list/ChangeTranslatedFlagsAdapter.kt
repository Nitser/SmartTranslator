package com.example.admin.smart_translator.ui.change_translated_flags.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.entities.Language
import java.util.ArrayList

class ChangeTranslatedFlagsAdapter : RecyclerView.Adapter<ChangeTranslatedFlagsListViewHolder>() {

    val flagList = ArrayList<Language>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ChangeTranslatedFlagsListViewHolder {
        val view = LayoutInflater
                .from(viewGroup.context).inflate(R.layout.layout_scrollable_flag, viewGroup, false) as RelativeLayout
        return ChangeTranslatedFlagsListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChangeTranslatedFlagsListViewHolder, i: Int) {
        if (flagList.size != 0) {
            holder.flagIcon.setBackgroundResource(flagList[i % flagList.size].lanIconPath)
            holder.flagIcon.contentDescription = flagList[i % flagList.size].langCode.name
        }
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

}

