package com.example.admin.smart_translator.ui.photo_card_info.list

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.smart_translator.R

class TranslationResultListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val resultFrom: TextView = itemView.findViewById(R.id.layout_result_text_from)
    val resultTo: TextView = itemView.findViewById(R.id.layout_result_text_to)

}