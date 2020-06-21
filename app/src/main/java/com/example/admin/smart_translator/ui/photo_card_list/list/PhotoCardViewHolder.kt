package com.example.admin.smart_translator.ui.photo_card_list.list

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.smart_translator.entities.PhotoCard
import com.example.admin.smart_translator.R

class PhotoCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val photo: ImageView = itemView.findViewById(R.id.layout_photo_card_photo)
    val flagFrom: ImageView = itemView.findViewById(R.id.layout_photo_card_flag_from)
    val flagTo: ImageView = itemView.findViewById(R.id.layout_photo_card_flag_to)

    fun bind(item: PhotoCard, listener: PhotoCardAdapter.OnItemClickListener, id: Int) {
        itemView.setOnClickListener { listener.onItemClick(item, id, this) }
    }

}