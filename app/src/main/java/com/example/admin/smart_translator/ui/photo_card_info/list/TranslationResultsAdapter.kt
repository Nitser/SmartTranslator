package com.example.admin.smart_translator.ui.photo_card_info.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView

import com.example.admin.smart_translator.R

class TranslationResultsAdapter : RecyclerView.Adapter<TranslationResultsViewHolder>() {

    val fromResults = ArrayList<String>()
    val toResults = ArrayList<String>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): TranslationResultsViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_result_text, viewGroup, false) as RelativeLayout
        return TranslationResultsViewHolder(view)
    }

    override fun onBindViewHolder(holder: TranslationResultsViewHolder, i: Int) {
        holder.resultFrom.text = fromResults[i]
        holder.resultTo.text = toResults[i]
    }

    override fun getItemCount(): Int {
        return fromResults.size
    }

}
