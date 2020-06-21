package com.example.admin.smart_translator.ui.change_translated_flags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.admin.smart_translator.databinding.FragmentChangeSelectedFlagsBinding
import com.example.admin.smart_translator.layout_managers.CenterZoomLayoutManager
import com.example.admin.smart_translator.ui.change_translated_flags.list.ChangeTranslatedFlagsAdapter
import com.example.admin.smart_translator.view_model.CurrentPhotoCardViewModel
import com.example.admin.smart_translator.view_model.SelectedFlagsViewModel

class ChangeTranslatedFlagsFragment : Fragment() {

    private val selectedFlagsViewModel: SelectedFlagsViewModel by activityViewModels()
    private val currentPhotoCardViewModel: CurrentPhotoCardViewModel by activityViewModels()
    private lateinit var adapter: ChangeTranslatedFlagsAdapter
    private lateinit var binding: FragmentChangeSelectedFlagsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChangeSelectedFlagsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManagerFrom = CenterZoomLayoutManager(requireContext(), 1, false, binding.changeSelectedFlagsFromText)
        binding.changeSelectedFlagsListFlagFrom.layoutManager = layoutManagerFrom
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.changeSelectedFlagsListFlagFrom)
        val adapter = ChangeTranslatedFlagsAdapter()
        adapter.flagList.addAll(selectedFlagsViewModel.getSelectedFlags().value!!)
        binding.changeSelectedFlagsListFlagFrom.adapter = adapter

        val layoutManagerTo = CenterZoomLayoutManager(requireContext(), 1, false, binding.changeSelectedFlagsToText)
        binding.changeSelectedFlagsListFlagTo.layoutManager = layoutManagerTo
        val snapHelper2 = LinearSnapHelper()
        snapHelper2.attachToRecyclerView(binding.changeSelectedFlagsListFlagTo)
        val adapter2 = ChangeTranslatedFlagsAdapter()
        adapter2.flagList.addAll(selectedFlagsViewModel.getSelectedFlags().value!!)
        binding.changeSelectedFlagsListFlagTo.adapter = adapter2

        binding.changeSelectedFlagsListFlagFrom.postDelayed({ binding.changeSelectedFlagsListFlagFrom.smoothScrollToPosition(7) }, 0)
        binding.changeSelectedFlagsListFlagTo.postDelayed({ binding.changeSelectedFlagsListFlagTo.smoothScrollToPosition(7) }, 0)

//        flagsViewModel.getFlags().observe(viewLifecycleOwner, Observer<ArrayList<Language>> { item ->
//            val checkedFlags = item.filter { it.isChecked }
//            if (checkedFlags.isEmpty()) {
//                selectedFlagsViewModel.setDefaultFlags()
//            } else {
//                selectedFlagsViewModel.setSelectedFlags(ArrayList(checkedFlags))
//            }
//        })
    }
}
