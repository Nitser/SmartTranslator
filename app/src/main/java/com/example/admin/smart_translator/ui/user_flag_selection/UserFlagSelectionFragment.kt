package com.example.admin.smart_translator.ui.user_flag_selection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.databinding.FragmentSelectFlagListBinding
import com.example.admin.smart_translator.model.Flag
import com.example.admin.smart_translator.ui.user_flag_selection.list.UserFlagSelectionAdapter
import com.example.admin.smart_translator.ui.user_flag_selection.list.UserFlagSelectionViewHolder
import com.example.admin.smart_translator.view_model.AllFlagsViewModel
import com.example.admin.smart_translator.view_model.SelectedFlagsViewModel

class UserFlagSelectionFragment : Fragment() {

    private val selectedFlagsViewModel: SelectedFlagsViewModel by activityViewModels()
    private val allFlagsViewModel: AllFlagsViewModel by activityViewModels()
    private lateinit var adapter: UserFlagSelectionAdapter
    private lateinit var binding: FragmentSelectFlagListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_select_flags, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_select_flags_refresh -> {
                adapter.allFlags.map { it.isChecked = false }
                adapter.notifyDataSetChanged()
                return true
            }
            R.id.menu_select_flags_done -> {
                selectedFlagsViewModel.setSelectedFlags(ArrayList(adapter.allFlags.filter { it.isChecked }))
                requireActivity().findNavController(R.id.nav_host_fragment_home).popBackStack()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSelectFlagListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = LinearLayoutManager(requireContext())
        adapter = UserFlagSelectionAdapter(object : UserFlagSelectionAdapter.OnItemClickListener {
            override fun onItemClick(item: Flag, id: Int, userFlagSelectionViewHolder: UserFlagSelectionViewHolder) {
                item.isChecked = !item.isChecked
                userFlagSelectionViewHolder.checkBox.isChecked = item.isChecked
            }
        })
        adapter.allFlags.addAll(
                ArrayList(allFlagsViewModel.getAllFlags().value!!.map { itt ->
                    if (selectedFlagsViewModel.getSelectedFlags().value!!.find { it.langCode.name == itt.langCode.name } != null) {
                        itt.isChecked = true
                        itt
                    } else
                        itt
                })
        )
        binding.selectFlagList.layoutManager = layoutManager
        binding.selectFlagList.adapter = adapter
    }

}
