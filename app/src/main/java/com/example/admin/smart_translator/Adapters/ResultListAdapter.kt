package com.example.admin.smart_translator.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView

import com.example.admin.smart_translator.R

class ResultListAdapter(private val fromData: List<String>, private val toData: List<String>, private val width: Int) : androidx.recyclerview.widget.RecyclerView.Adapter<ResultListAdapter.MyViewHolder>() {

    class MyViewHolder(var mView: RelativeLayout) : androidx.recyclerview.widget.RecyclerView.ViewHolder(mView)

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.result_text_view, viewGroup, false) as RelativeLayout
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull myViewHolder: MyViewHolder, i: Int) {
        val from = myViewHolder.mView.getChildAt(0) as TextView
        val to = myViewHolder.mView.getChildAt(1) as TextView
        from.layoutParams.width = width / 2
        to.layoutParams.width = width / 2
        from.text = fromData[i]
        to.text = toData[i]
    }

    override fun getItemCount(): Int {
        return fromData.size
    }
}
