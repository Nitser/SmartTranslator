package com.example.admin.smart_translator.ui.choose_translation_flags.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.model.Flag

class CurrentFlagsAdapter : RecyclerView.Adapter<CurrentFlagsViewHolder>() {

    val currentFlags = ArrayList<Flag>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CurrentFlagsViewHolder {
        val view = LayoutInflater
                .from(viewGroup.context).inflate(R.layout.layout_scrollable_flag, viewGroup, false) as RelativeLayout
        return CurrentFlagsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrentFlagsViewHolder, i: Int) {
        if (currentFlags.size != 0) {
            holder.flagIcon.setBackgroundResource(currentFlags[i % currentFlags.size].flagIconPath)
            holder.flagIcon.contentDescription = currentFlags[i % currentFlags.size].langCode.name
        }
    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }

}

