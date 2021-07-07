package com.example.admin.smart_translator.ui.photo_card_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.app.MainActivity
import com.example.admin.smart_translator.app.PhotoCardType
import com.example.admin.smart_translator.databinding.FragmentPhotoCardListBinding
import com.example.admin.smart_translator.model.PhotoCard
import com.example.admin.smart_translator.ui.photo_card_list.list.PhotoCardAdapter
import com.example.admin.smart_translator.ui.photo_card_list.list.PhotoCardViewHolder
import com.example.admin.smart_translator.view_model.SelectedPhotoCardViewModel
import com.example.admin.smart_translator.view_model.UserViewModel

class PhotoCardFragment : Fragment() {

    private lateinit var binding: FragmentPhotoCardListBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val selectedPhotoCardViewModel: SelectedPhotoCardViewModel by activityViewModels()
    private lateinit var photoCardType: PhotoCardType

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(false)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        binding = FragmentPhotoCardListBinding.inflate(inflater, container, false)
        photoCardType = arguments?.getSerializable(MainActivity.fragmentType) as PhotoCardType
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManager = androidx.recyclerview.widget.GridLayoutManager(view.context, 2)
        binding.photoCardList.layoutManager = layoutManager
        binding.photoCardList.setHasFixedSize(true)

        val adapter = PhotoCardAdapter(object : PhotoCardAdapter.OnItemClickListener {
            override fun onItemClick(item: PhotoCard, id: Int, boardHolder: PhotoCardViewHolder) {
                selectedPhotoCardViewModel.setSelectedPhotoCard(item)
                activity!!.findNavController(R.id.nav_host_fragment_home)
                        .navigate(PhotoCardFragmentDirections.actionPhotoCardFragmentToPhotoCardInfoFragment())
            }
        })

        if (photoCardType == PhotoCardType.HistoryList) {
            if (userViewModel.getHistoryPhotoCards().value!!.isNotEmpty()) {
                adapter.photoCards.addAll(userViewModel.getHistoryPhotoCards().value!!)
                userViewModel.getHistoryPhotoCards().observe(viewLifecycleOwner, Observer<ArrayList<PhotoCard>> { item ->
                    adapter.photoCards.clear()
                    adapter.photoCards.addAll(item)
                    adapter.notifyDataSetChanged()
                })
            }
        } else {
            if (userViewModel.getFavoritePhotoCards().value!!.isNotEmpty()) {
                adapter.photoCards.addAll(userViewModel.getFavoritePhotoCards().value!!)
                userViewModel.getFavoritePhotoCards().observe(viewLifecycleOwner, Observer<ArrayList<PhotoCard>> { item ->
                    adapter.photoCards.clear()
                    adapter.photoCards.addAll(item)
                    adapter.notifyDataSetChanged()
                })
            }
        }
        binding.photoCardList.adapter = adapter
    }

}
