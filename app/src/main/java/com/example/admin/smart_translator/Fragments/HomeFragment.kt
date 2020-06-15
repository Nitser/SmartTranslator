package com.example.admin.smart_translator.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView

import com.example.admin.smart_translator.app.FlagListActivity
import com.example.admin.smart_translator.app.SecondActivity
import com.example.admin.smart_translator.Adapters.FlagAdapter
import com.example.admin.smart_translator.Models.Language
import com.example.admin.smart_translator.Models.PhotoCard
import com.example.admin.smart_translator.Models.User
import com.example.admin.smart_translator.LayoutManagers.CenterZoomLayoutManager
import com.example.admin.smart_translator.R

class HomeFragment : androidx.fragment.app.Fragment() {

    private var from: TextView? = null
    private var to: TextView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {
        from = getView()!!.findViewById(R.id.from_lan)
        to = getView()!!.findViewById(R.id.to_lan)

        val flagView = getView()!!.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.list_flag)
        val layoutManager = CenterZoomLayoutManager(getView()!!.context, 1, false, from)
        flagView.layoutManager = layoutManager
        val snapHelper = androidx.recyclerview.widget.LinearSnapHelper()
        snapHelper.attachToRecyclerView(flagView)

        val flagView2 = getView()!!.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.list_flag2)
        val layoutManager2 = CenterZoomLayoutManager(getView()!!.context, 1, false, to)
        flagView2.layoutManager = layoutManager2
        val snapHelper2 = androidx.recyclerview.widget.LinearSnapHelper()
        snapHelper2.attachToRecyclerView(flagView2)
        if (User.currentFlags.size == 0) {
            User.currentFlags.add(Language.ENGLISH)
            User.currentFlags.add(Language.RUSSIAN)
        }

        val adapter = FlagAdapter(User.currentFlags, getView()!!.context)
        val adapter2 = FlagAdapter(User.currentFlags, getView()!!.context)
        flagView.adapter = adapter
        flagView2.adapter = adapter2

        flagView.postDelayed({ flagView.smoothScrollToPosition(7) }, 0)
        flagView2.postDelayed({ flagView2.smoothScrollToPosition(7) }, 0)

        val button = getView()!!.findViewById<ImageButton>(R.id.startPhoto)
        button.setOnClickListener { v ->
            val photoCard = PhotoCard(from!!.text.toString().toLowerCase(), to!!.text.toString().toLowerCase())
            User.photoCardStorage.add(photoCard)
            val intent = Intent(getView()!!.context, SecondActivity::class.java)
            intent.putExtra("PhotoCard_Parent", "home")
            intent.putExtra("Option", "Photo")
            startActivity(intent)
        }

        val button2 = getView()!!.findViewById<Button>(R.id.chooseFlag)
        button2.setOnClickListener { v ->
            val intent = Intent(getView()!!.context, FlagListActivity::class.java)
            startActivity(intent)
        }

        val button3 = getView()!!.findViewById<ImageButton>(R.id.startGallery)
        button3.setOnClickListener { v ->
            val photoCard = PhotoCard(from!!.text.toString().toLowerCase(), to!!.text.toString().toLowerCase())
            User.photoCardStorage.add(photoCard)
            val intent = Intent(getView()!!.context, SecondActivity::class.java)
            intent.putExtra("PhotoCard_Parent", "home")
            intent.putExtra("Option", "Gallery")
            startActivity(intent)

        }


    }

    companion object {

        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }

}
