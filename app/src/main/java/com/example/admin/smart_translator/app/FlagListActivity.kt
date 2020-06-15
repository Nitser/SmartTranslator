package com.example.admin.smart_translator.app

import android.os.Bundle
import androidx.core.app.NavUtils
import androidx.appcompat.app.AppCompatActivity
import android.view.View

import com.example.admin.smart_translator.Adapters.FlagListAdapter
import com.example.admin.smart_translator.Models.Language
import com.example.admin.smart_translator.Models.User
import com.example.admin.smart_translator.R

class FlagListActivity : AppCompatActivity() {

    private var adapter: androidx.recyclerview.widget.RecyclerView.Adapter<*>? = null

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flag_list_layout)

        val flagView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.flags_list)
        val layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        flagView.layoutManager = layoutManager
        adapter = FlagListAdapter(this)

        //        flagView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
        //            @Override
        //            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        //                return false;
        //            }
        //
        //            @Override
        //            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        //
        //            }
        //
        //            @Override
        //            public void onRequestDisallowInterceptTouchEvent(boolean b) {
        //
        //            }
        //        });
        flagView.adapter = adapter

    }

    fun onClickBackButton(view: View) {
        val ad = adapter as FlagListAdapter?
        val checked = ad!!.checked
        User.currentFlags.clear()
        for (i in 0 until Language.values().size) {
            if (checked[i]) {
                User.currentFlags.add(Language.values()[i])
            }
        }
        val intent = NavUtils.getParentActivityIntent(this)
        NavUtils.navigateUpTo(this, intent!!)
    }

    fun onClickBackReset(view: View) {
        val ad = adapter as FlagListAdapter?
        ad!!.reset()
    }
}
