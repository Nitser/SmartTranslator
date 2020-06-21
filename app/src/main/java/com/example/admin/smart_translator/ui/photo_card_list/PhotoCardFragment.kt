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
import com.example.admin.smart_translator.databinding.FragmentPhotoCardListBinding
import com.example.admin.smart_translator.entities.PhotoCard
import com.example.admin.smart_translator.ui.photo_card_list.list.PhotoCardAdapter
import com.example.admin.smart_translator.ui.photo_card_list.list.PhotoCardViewHolder
import com.example.admin.smart_translator.view_model.CurrentPhotoCardViewModel
import com.example.admin.smart_translator.view_model.UserViewModel
import java.util.ArrayList

class PhotoCardFragment : Fragment() {

    private lateinit var binding: FragmentPhotoCardListBinding
    private val userViewModel: UserViewModel by activityViewModels()
    private val currentPhotoCardViewModel: CurrentPhotoCardViewModel by activityViewModels()
    private lateinit var photoCardType: PhotoCardType

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(false)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        binding = FragmentPhotoCardListBinding.inflate(inflater, container, false)
        photoCardType = arguments?.getSerializable(MainActivity.photoCardBundleTag) as PhotoCardType
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.photoCardList.setHasFixedSize(true)
        val layoutManager = androidx.recyclerview.widget.GridLayoutManager(view.context, 2)
        binding.photoCardList.layoutManager = layoutManager

        val adapter = PhotoCardAdapter(view.context, object : PhotoCardAdapter.OnItemClickListener {
            override fun onItemClick(item: PhotoCard, id: Int, boardHolder: PhotoCardViewHolder) {
                currentPhotoCardViewModel.setCurrentPhotoCards(item)
                activity!!.findNavController(R.id.nav_host_fragment_home)
                        .navigate(PhotoCardFragmentDirections.actionPhotoCardFragmentToPhotoCardInfoFragment())
            }
        })
        if (photoCardType == PhotoCardType.HistoryList) {
            if (userViewModel.getHistoryPhotoCards().value!!.isNotEmpty()) {
                adapter.photoCardList.addAll(userViewModel.getHistoryPhotoCards().value!!)
                userViewModel.getHistoryPhotoCards().observe(viewLifecycleOwner, Observer<ArrayList<PhotoCard>> { item ->
                    adapter.photoCardList.clear()
                    adapter.photoCardList.addAll(item)
                    adapter.notifyDataSetChanged()
                })
            }
        } else {
            if (userViewModel.getFavoritePhotoCards().value!!.isNotEmpty()) {
                adapter.photoCardList.addAll(userViewModel.getFavoritePhotoCards().value!!)
                userViewModel.getFavoritePhotoCards().observe(viewLifecycleOwner, Observer<ArrayList<PhotoCard>> { item ->
                    adapter.photoCardList.clear()
                    adapter.photoCardList.addAll(item)
                    adapter.notifyDataSetChanged()
                })
            }
        }
        binding.photoCardList.adapter = adapter
    }

}
