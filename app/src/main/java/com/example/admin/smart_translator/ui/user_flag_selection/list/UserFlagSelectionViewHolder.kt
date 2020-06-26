package com.example.admin.smart_translator.ui.user_flag_selection.list

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.model.Flag

class UserFlagSelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val checkBox: CheckBox = itemView.findViewById(R.id.layout_select_flag_choose)
    val countryName: TextView = itemView.findViewById(R.id.layout_select_flag_country_name)
    val flagIcon: ImageView = itemView.findViewById(R.id.layout_select_flag_flag_image)

    fun bind(item: Flag, listener: UserFlagSelectionAdapter.OnItemClickListener, id: Int) {
        itemView.setOnClickListener { listener.onItemClick(item, id, this) }
    }
}