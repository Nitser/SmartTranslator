package com.example.admin.smarttranslator.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout

import com.example.admin.smarttranslator.Activities.SecondActivity
import com.example.admin.smarttranslator.Models.PhotoCard
import com.example.admin.smarttranslator.R
import com.example.admin.smarttranslator.Services.PhotoService

class HistoryAdapter(private val photos: List<PhotoCard>, private val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {
    private val photoService: PhotoService

    internal class HistoryViewHolder(var mView: RelativeLayout) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView) {

        init {
            itemView.setOnClickListener { v ->
                val context = v.context
                val intent = Intent(context, SecondActivity::class.java)
                intent.putExtra("PhotoCard_Position", adapterPosition)
                intent.putExtra("PhotoCard_Parent", "history")
                context.startActivity(intent)
            }
        }

    }

    init {
        photoService = PhotoService()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): HistoryAdapter.HistoryViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.card_history_layout, viewGroup, false) as RelativeLayout
        return HistoryViewHolder(view)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onBindViewHolder(@NonNull myViewHolder: HistoryAdapter.HistoryViewHolder, i: Int) {
        val cardView = myViewHolder.mView.getChildAt(0) as androidx.cardview.widget.CardView
        val layout = cardView.getChildAt(0) as androidx.constraintlayout.widget.ConstraintLayout

        val imageView = layout.getChildAt(0) as ImageView
        val linearLayout = layout.getChildAt(1) as LinearLayout

        val flagFrom = linearLayout.getChildAt(0) as ImageView
        val flagTo = linearLayout.getChildAt(2) as ImageView

        val bitmap = photoService.decodingPhoto(photos[i].filePath, 150, 200)
        imageView.setImageBitmap(bitmap)

        val fromId = myViewHolder.mView.resources.getIdentifier("ic_" + photos[i].lanFrom!!, "mipmap", myViewHolder.mView.context.packageName)
        val toId = myViewHolder.mView.resources.getIdentifier("ic_" + photos[i].lanTo!!, "mipmap", myViewHolder.mView.context.packageName)
        flagFrom.setBackgroundResource(fromId)
        flagTo.setBackgroundResource(toId)

    }

    override fun getItemCount(): Int {
        return photos.size
    }

}