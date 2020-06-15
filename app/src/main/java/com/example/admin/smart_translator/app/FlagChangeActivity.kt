package com.example.admin.smart_translator.app

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.TextView

import com.example.admin.smart_translator.Adapters.FlagAdapter
import com.example.admin.smart_translator.Models.User
import com.example.admin.smart_translator.LayoutManagers.CenterZoomLayoutManager
import com.example.admin.smart_translator.R

class FlagChangeActivity : AppCompatActivity() {

    private var from: TextView? = null
    private var to: TextView? = null
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.flag_change_layout)

        from = findViewById(R.id.from_lan)
        to = findViewById(R.id.to_lan)

        val flagView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.list_flag)
        val layoutManager = CenterZoomLayoutManager(this, 1, false, from)
        flagView.layoutManager = layoutManager
        val snapHelper = androidx.recyclerview.widget.LinearSnapHelper()
        snapHelper.attachToRecyclerView(flagView)
        val adapter = FlagAdapter(User.currentFlags, this)
        flagView.adapter = adapter

        val flagView2 = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.list_flag2)
        val layoutManager2 = CenterZoomLayoutManager(this, 1, false, to)
        flagView2.layoutManager = layoutManager2
        val snapHelper2 = androidx.recyclerview.widget.LinearSnapHelper()
        snapHelper2.attachToRecyclerView(flagView2)
        val adapter2 = FlagAdapter(User.currentFlags, this)
        flagView2.adapter = adapter2

        flagView.postDelayed({ flagView.smoothScrollToPosition(7) }, 0)
        flagView2.postDelayed({ flagView2.smoothScrollToPosition(7) }, 0)
    }

    fun onClickBackButton(view: View) {
        val intent = Intent()

        intent.putExtra("from", from!!.text.toString().toLowerCase())
        intent.putExtra("to", to!!.text.toString().toLowerCase())

        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
