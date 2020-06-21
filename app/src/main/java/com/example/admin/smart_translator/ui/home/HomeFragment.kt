package com.example.admin.smart_translator.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.databinding.FragmentHomeBinding
import com.example.admin.smart_translator.entities.Language
import com.example.admin.smart_translator.entities.PhotoCard
import com.example.admin.smart_translator.layout_managers.CenterZoomLayoutManager
import com.example.admin.smart_translator.model.FileStorageService
import com.example.admin.smart_translator.ui.change_translated_flags.list.ChangeTranslatedFlagsAdapter
import com.example.admin.smart_translator.view_model.CurrentPhotoCardViewModel
import com.example.admin.smart_translator.view_model.FlagsViewModel
import com.example.admin.smart_translator.view_model.SelectedFlagsViewModel
import com.example.admin.smart_translator.view_model.UserViewModel
import java.io.IOException

class HomeFragment : Fragment() {

    private val selectedFlagsViewModel: SelectedFlagsViewModel by activityViewModels()
    private val flagsViewModel: FlagsViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()
    private val currentPhotoCardViewModel: CurrentPhotoCardViewModel by activityViewModels()

    private lateinit var binding: FragmentHomeBinding
    private val errorToastString = "Ошибка загрузки фотографии"
    private lateinit var newPhotoCard: PhotoCard

    private val requestCodePhoto = 1
    private val requestCodeGallery = 2

    private lateinit var adapterFrom: ChangeTranslatedFlagsAdapter
    private lateinit var adapterTo: ChangeTranslatedFlagsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(false)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val layoutManagerFrom = CenterZoomLayoutManager(view.context, 1, false, binding.homeFromText)
        binding.homeFlagListFrom.layoutManager = layoutManagerFrom
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.homeFlagListFrom)

        selectedFlagsViewModel.getSelectedFlags().observe(viewLifecycleOwner, Observer<ArrayList<Language>> { item ->
            adapterFrom.flagList.addAll(item)
            adapterTo.flagList.addAll(item)
            adapterFrom.notifyDataSetChanged()
            adapterTo.notifyDataSetChanged()
        })

        val layoutManagerTo = CenterZoomLayoutManager(view.context, 1, false, binding.homeToText)
        binding.homeFlagListTo.layoutManager = layoutManagerTo
        val snapHelper2 = LinearSnapHelper()
        snapHelper2.attachToRecyclerView(binding.homeFlagListTo)
        if (selectedFlagsViewModel.getSelectedFlags().value == null || selectedFlagsViewModel.getSelectedFlags().value?.size == 0) {
            selectedFlagsViewModel.setDefaultFlags(flagsViewModel.getFlags().value!!)
        }

        adapterFrom = ChangeTranslatedFlagsAdapter()
        adapterTo = ChangeTranslatedFlagsAdapter()

        with(binding) {
            homeFlagListFrom.adapter = adapterFrom
            homeFlagListTo.adapter = adapterTo

            homeFlagListFrom.postDelayed({ homeFlagListFrom.smoothScrollToPosition(7) }, 0)
            homeFlagListTo.postDelayed({ homeFlagListTo.smoothScrollToPosition(7) }, 0)

            homeButtonPhoto.setOnClickListener { startDispatchTakePictureIntent() }
            homeButtonGallery.setOnClickListener { startDispatchGalleryIntent() }

            homeButtonChooseFlags.setOnClickListener {
                requireActivity().findNavController(R.id.nav_host_fragment_home)
                        .navigate(HomeFragmentDirections.actionHomeFragmentToFlagListFragment())
            }
        }
    }

    private fun initialPhotoCard(): PhotoCard {
        newPhotoCard = PhotoCard()
        newPhotoCard.flagFromRes = requireContext().resources.getIdentifier(
                "ic_${(binding.homeFlagListFrom.layoutManager as CenterZoomLayoutManager).getCurrentFlagIndex()}"
                , "mipmap"
                , requireContext().packageName
        )

        newPhotoCard.flagToRes = requireContext().resources.getIdentifier(
                "ic_${(binding.homeFlagListTo.layoutManager as CenterZoomLayoutManager).getCurrentFlagIndex()}"
                , "mipmap"
                , requireContext().packageName
        )
        return newPhotoCard
    }

    private fun startDispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            try {
                val fileStorageService = FileStorageService()
                val photoFile = fileStorageService.createImageFile(requireContext())
                initialPhotoCard()
                newPhotoCard.filePath = photoFile.absolutePath
                val photoURI = FileProvider.getUriForFile(requireContext()
                        , "com.example.android.fileprovider", photoFile)

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, requestCodePhoto)
            } catch (ex: IOException) {
                Toast.makeText(requireContext(), errorToastString, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startDispatchGalleryIntent() {
        initialPhotoCard()
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCodeGallery)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) =
            if (requestCode == requestCodePhoto && intent == null) {
                newPhotoCard = currentPhotoCardViewModel.saveNewPhotoToStorage(newPhotoCard)
                currentPhotoCardViewModel.setCurrentPhotoCards(newPhotoCard)
                userViewModel.addHistoryPhotoCards(newPhotoCard)
                userViewModel.savePhotoCardToStorage(requireContext(), newPhotoCard)
                requireActivity().findNavController(R.id.nav_host_fragment_home)
                        .navigate(HomeFragmentDirections.actionHomeFragmentToPhotoCardInfoFragment())
            } else if (requestCode == requestCodeGallery && resultCode == Activity.RESULT_OK && intent != null && intent.data != null) {
                if (intent.data != null) {
                    newPhotoCard = currentPhotoCardViewModel.getPhotoFromGallery(requireContext(), newPhotoCard, intent.data!!)
                    currentPhotoCardViewModel.setCurrentPhotoCards(newPhotoCard)
                    userViewModel.addHistoryPhotoCards(newPhotoCard)
                    userViewModel.savePhotoCardToStorage(requireContext(), newPhotoCard)
                    requireActivity().findNavController(R.id.nav_host_fragment_home)
                            .navigate(HomeFragmentDirections.actionHomeFragmentToPhotoCardInfoFragment())
                } else
                    Toast.makeText(requireContext(), errorToastString, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), errorToastString, Toast.LENGTH_SHORT).show()
            }

}
