package com.example.admin.smarttranslator.Adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView

import com.example.admin.smarttranslator.Models.Language
import com.example.admin.smarttranslator.R

class FlagListAdapter(private val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<FlagListAdapter.FlagViewHolder>() {

    private val languages = Language.values()
    var checked: BooleanArray
        internal set
    internal var isReset = false

    init {
        checked = BooleanArray(languages.size)
    }

    class FlagViewHolder(var mView: RelativeLayout) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView)

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): FlagViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.list_flags_view, viewGroup, false) as RelativeLayout
        return FlagViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull flagViewHolder: FlagViewHolder, i: Int) {
        val resId = flagViewHolder.mView.resources.getIdentifier("ic_" + languages[i].toString(), "mipmap", context.packageName)

        val checkBox = flagViewHolder.mView.getChildAt(0) as CheckBox
        checkBox.isChecked = checked[i]
        checkBox.setOnClickListener { v -> checked[i] = !checked[i] }
        if (isReset) {
            checkBox.isChecked = false
            checked[i] = false
        }


        val flag = flagViewHolder.mView.getChildAt(1) as ImageView
        flag.setBackgroundResource(resId)
        flag.contentDescription = languages[i].toString()

        val name = flagViewHolder.mView.getChildAt(2) as TextView
        name.setText(languages[i].name.substring(0, 1) + languages[i].name.substring(1).toLowerCase())
    }

    fun reset() {
        isReset = true
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return languages.size
    }
}
