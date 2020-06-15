package com.example.admin.smart_translator.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.admin.smart_translator.R

class CoffeeFragment : androidx.fragment.app.Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.coffee_layout, container, false)
    }

    override fun onViewCreated(@NonNull view: View, @Nullable savedInstanceState: Bundle?) {

    }

    companion object {

        fun newInstance(): CoffeeFragment {
            return CoffeeFragment()
        }
    }
}
