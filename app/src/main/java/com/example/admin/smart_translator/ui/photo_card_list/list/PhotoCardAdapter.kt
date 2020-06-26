package com.example.admin.smart_translator.ui.photo_card_list.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.model.PhotoCard
import com.example.admin.smart_translator.utils.PhotoUtils
import java.util.ArrayList

class PhotoCardAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<PhotoCardViewHolder>() {

    var photoCards: ArrayList<PhotoCard> = ArrayList()
    private val photoService = PhotoUtils()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PhotoCardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_photo_card, viewGroup, false) as RelativeLayout
        return PhotoCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoCardViewHolder, i: Int) {
        val photoCard = photoCards[i]
        val bitmap = photoService.decoding(photoCard.photoFilePathFromStorage)

        holder.photo.setImageBitmap(bitmap)
        holder.flagFrom.setBackgroundResource(photoCard.flagIconPathFromLang)
        holder.flagTo.setBackgroundResource(photoCard.flagIconPathToLang)

        holder.bind(photoCard, listener, i)
    }

    override fun getItemCount(): Int {
        return photoCards.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface OnItemClickListener {
        fun onItemClick(item: PhotoCard, id: Int, boardHolder: PhotoCardViewHolder)
    }

}