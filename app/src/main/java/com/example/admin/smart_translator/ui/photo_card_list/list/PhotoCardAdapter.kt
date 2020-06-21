package com.example.admin.smart_translator.ui.photo_card_list.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.smart_translator.entities.PhotoCard
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.model.PhotoService
import java.util.ArrayList

class PhotoCardAdapter(val context: Context, private val listener: OnItemClickListener) : RecyclerView.Adapter<PhotoCardViewHolder>() {
    var photoCardList: ArrayList<PhotoCard> = ArrayList()
    private val photoService = PhotoService()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): PhotoCardViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.layout_photo_card, viewGroup, false) as RelativeLayout
        return PhotoCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotoCardViewHolder, i: Int) {
        val photoCard = photoCardList[i]
        val bitmap = photoService.decodingPhoto(photoCard.filePath)

        holder.photo.setImageBitmap(bitmap)
        holder.flagFrom.setBackgroundResource(photoCard.flagFromRes)
        holder.flagTo.setBackgroundResource(photoCard.flagToRes)

        holder.bind(photoCard, listener, i)
    }

    override fun getItemCount(): Int {
        return photoCardList.size
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