package com.example.admin.smart_translator.ui.choose_translation_flags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.admin.smart_translator.databinding.FragmentChangeSelectedFlagsBinding
import com.example.admin.smart_translator.layout_managers.CenterZoomFlagLayoutManager
import com.example.admin.smart_translator.ui.choose_translation_flags.list.CurrentFlagsAdapter
import com.example.admin.smart_translator.view_model.SelectedFlagsViewModel

class ChooseTranslationFlagsFragment : Fragment() {

    private val selectedFlagsViewModel: SelectedFlagsViewModel by activityViewModels()
    private lateinit var binding: FragmentChangeSelectedFlagsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChangeSelectedFlagsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManagerFrom = CenterZoomFlagLayoutManager(requireContext(), 1, false, binding.changeSelectedFlagsFromText)
        binding.changeSelectedFlagsListFlagFrom.layoutManager = layoutManagerFrom
        LinearSnapHelper().attachToRecyclerView(binding.changeSelectedFlagsListFlagFrom)
        val adapterFrom = CurrentFlagsAdapter()
        adapterFrom.currentFlags.addAll(selectedFlagsViewModel.getSelectedFlags().value!!)
        binding.changeSelectedFlagsListFlagFrom.adapter = adapterFrom

        val layoutManagerTo = CenterZoomFlagLayoutManager(requireContext(), 1, false, binding.changeSelectedFlagsToText)
        binding.changeSelectedFlagsListFlagTo.layoutManager = layoutManagerTo
        LinearSnapHelper().attachToRecyclerView(binding.changeSelectedFlagsListFlagTo)
        val adapterTo = CurrentFlagsAdapter()
        adapterTo.currentFlags.addAll(selectedFlagsViewModel.getSelectedFlags().value!!)
        binding.changeSelectedFlagsListFlagTo.adapter = adapterTo

        binding.changeSelectedFlagsListFlagFrom.postDelayed({ binding.changeSelectedFlagsListFlagFrom.smoothScrollToPosition(7) }, 0)
        binding.changeSelectedFlagsListFlagTo.postDelayed({ binding.changeSelectedFlagsListFlagTo.smoothScrollToPosition(7) }, 0)

//        flagsViewModel.getAllFlags().observe(viewLifecycleOwner, Observer<ArrayList<Flag>> { item ->
//            val checkedFlags = item.filter { it.isChecked }
//            if (checkedFlags.isEmpty()) {
//                selectedFlagsViewModel.setDefaultSelectedFlags()
//            } else {
//                selectedFlagsViewModel.setSelectedFlags(ArrayList(checkedFlags))
//            }
//        })
    }
}
