package com.example.admin.smart_translator.ui.photo_card_info.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView

import com.example.admin.smart_translator.R

class TranslationResultListAdapter : RecyclerView.Adapter<TranslationResultListViewHolder>() {

    val fromData = ArrayList<String>()
    val toData = ArrayList<String>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TranslationResultListViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_result_text, viewGroup, false) as RelativeLayout
        return TranslationResultListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TranslationResultListViewHolder, i: Int) {
        holder.resultFrom.text = fromData[i]
        holder.resultTo.text = toData[i]
    }

    override fun getItemCount(): Int {
        return fromData.size
    }

}
