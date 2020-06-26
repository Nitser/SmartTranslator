package com.example.admin.smart_translator.ui.user_flag_selection.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.model.Flag
import java.util.Locale

class UserFlagSelectionAdapter(private val listener: OnItemClickListener) : RecyclerView.Adapter<UserFlagSelectionViewHolder>() {

    lateinit var allFlags: ArrayList<Flag>

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): UserFlagSelectionViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_select_flag_item, viewGroup, false) as RelativeLayout
        return UserFlagSelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserFlagSelectionViewHolder, i: Int) {
        val flag = allFlags[i]

        holder.checkBox.isChecked = flag.isChecked
        holder.checkBox.setOnClickListener { flag.isChecked = !flag.isChecked }

        holder.flagIcon.setBackgroundResource(flag.flagIconPath)
        holder.flagIcon.contentDescription = allFlags[i].toString()

        holder.countryName.text = flag.langCode.name.toLowerCase(Locale.ROOT)
        holder.bind(allFlags[i], listener, i)
    }

    override fun getItemCount(): Int {
        return allFlags.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface OnItemClickListener {
        fun onItemClick(item: Flag, id: Int, userFlagSelectionViewHolder: UserFlagSelectionViewHolder)
    }
}
