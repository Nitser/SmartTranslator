package com.example.admin.smart_translator.Adapters

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout

import com.example.admin.smart_translator.Models.Language
import com.example.admin.smart_translator.R

import java.util.ArrayList

class FlagAdapter(private val flagData: List<Language>, private val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<FlagAdapter.FlagViewHolder>() {
    private val flagId: List<Int>

    internal class FlagViewHolder(var mView: RelativeLayout) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView)

    init {
        flagId = ArrayList()
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): FlagViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.flags_view, viewGroup, false) as RelativeLayout
        return FlagViewHolder(view)
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    override fun onBindViewHolder(@NonNull myViewHolder: FlagViewHolder, i: Int) {
        val resId = myViewHolder.mView.resources.getIdentifier("ic_" + flagData[i % flagData.size].toString(), "mipmap", context.packageName)

        val flag = myViewHolder.mView.getChildAt(0) as ImageView
        flag.setBackgroundResource(resId)
        flag.contentDescription = flagData[i % flagData.size].toString()

    }

    override fun getItemCount(): Int {
        return Integer.MAX_VALUE
    }
}

