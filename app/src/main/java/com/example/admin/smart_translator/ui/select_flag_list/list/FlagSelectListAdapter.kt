package com.example.admin.smart_translator.ui.select_flag_list.list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.entities.Language
import java.util.Locale

class FlagSelectListAdapter(private val context: Context, private val listener: OnItemClickListener) : RecyclerView.Adapter<FlagSelectListViewHolder>() {

    private lateinit var languages: ArrayList<Language>

    fun getLanguageList(): ArrayList<Language> {
        return languages
    }

    fun setLanguageList(list: ArrayList<Language>) {
        languages = list
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): FlagSelectListViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_select_flag_item, viewGroup, false) as RelativeLayout
        return FlagSelectListViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlagSelectListViewHolder, i: Int) {
        val language = languages[i]

        holder.checkBox.isChecked = language.isChecked
        // Изменять модель в адаптерах - обычно не очень хорошая идея.
        // Лучше завести какую-нибудь внутреннюю переменную, если это состояние надо потом передать во вне.
        // Или можно сразу вызывать какой-либо листенер с сообщением об изменении состояния
        holder.checkBox.setOnClickListener { language.isChecked = !language.isChecked }

        holder.flagIcon.setBackgroundResource(language.lanIconPath)
        holder.flagIcon.contentDescription = languages[i].toString()

        holder.countryName.text = language.langCode.name.toLowerCase(Locale.ROOT)
        holder.bind(languages[i], listener, i)
    }

    override fun getItemCount(): Int {
        return languages.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    interface OnItemClickListener {
        fun onItemClick(item: Language, id: Int, boardHolder: FlagSelectListViewHolder)
    }
}
