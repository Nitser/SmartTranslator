package com.example.admin.smart_translator.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.admin.smart_translator.Adapters.FavoriteAdapter
import com.example.admin.smart_translator.Models.User
import com.example.admin.smart_translator.R

class FavoriteFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.history_fragment, container, false)
    }

    override fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {

        val recyclerView = getView()!!.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.historyView)
        recyclerView.setHasFixedSize(true)
        val layoutManager = androidx.recyclerview.widget.GridLayoutManager(getView()!!.context, 2)
        recyclerView.layoutManager = layoutManager

        if (User.likedPhotoCardStorage.size == 0) {
            //            photos.add(new PhotoCard("en", "ru"));
        } else {
            val adapter = FavoriteAdapter(User.likedPhotoCardStorage, getView()!!.context)
            recyclerView.adapter = adapter
        }


    }

    companion object {

        fun newInstance(): FavoriteFragment {
            return FavoriteFragment()
        }
    }
}
