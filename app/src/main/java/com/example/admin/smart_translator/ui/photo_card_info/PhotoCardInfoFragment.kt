package com.example.admin.smart_translator.ui.photo_card_info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.smart_translator.databinding.FragmentPhotoCardInfoBinding
import com.example.admin.smart_translator.model.PhotoCard
import com.example.admin.smart_translator.ui.photo_card_info.list.TranslationResultsAdapter
import com.example.admin.smart_translator.view_model.SelectedPhotoCardViewModel

class PhotoCardInfoFragment : Fragment() {

    private lateinit var binding: FragmentPhotoCardInfoBinding
    private lateinit var adapter: TranslationResultsAdapter
    private val selectedPhotoCardViewModel: SelectedPhotoCardViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPhotoCardInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        selectedPhotoCardViewModel.getSelectedPhotoCard().observe(viewLifecycleOwner, Observer<PhotoCard> { item ->
            adapter.fromResults.clear()
            adapter.toResults.clear()
            adapter.fromResults.addAll(item.translationFrom)
            adapter.toResults.addAll(item.translationTo)
            adapter.notifyDataSetChanged()

            initFromToFlagIcons()
        })
        onClickCheck()
    }

    private fun init() {
        with(selectedPhotoCardViewModel.getSelectedPhotoCard().value!!) {
            initFromToFlagIcons()
            binding.photoCardInfoLayoutFlagList.setOnClickListener { onClickFlagButton() }

            binding.photoCardInfoPhoto.setImageBitmap(photoBitmap)

            val layoutManager = LinearLayoutManager(requireContext())
            adapter = TranslationResultsAdapter()
            binding.photoCardInfoResultList.layoutManager = layoutManager
            binding.photoCardInfoResultList.adapter = adapter
            binding.photoCardInfoResultList.setHasFixedSize(true)
        }
    }

    private fun initFromToFlagIcons() {
        with(selectedPhotoCardViewModel.getSelectedPhotoCard().value!!) {
            binding.photoCardInfoFlagFrom.setBackgroundResource(flagIconPathFromLang)
            binding.photoCardInfoFlagTo.setBackgroundResource(flagIconPathToLang)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
//        if (requestCode == 3) {
//            with(selectedPhotoCardViewModel.getSelectedPhotoCard().value!!) {
////                lanFrom = intent!!.getStringExtra("from")
////                lanTo = intent.getStringExtra("to")
//                val fromId = resources.getIdentifier("ic_$lanFrom", "mipmap", requireContext().packageName)
//                val toId = resources.getIdentifier("ic_$lanTo", "mipmap", requireContext().packageName)
//
//                binding.photoCardInfoFlagFrom.setBackgroundResource(fromId)
//                binding.photoCardInfoFlagTo.setBackgroundResource(toId)
//
//                if (translationFrom.isNotEmpty()) {
//                    translationFrom.clear()
//                    translationTo.clear()
//                    binding.photoCardInfoResultList.adapter!!.notifyDataSetChanged()
//                }
////                val translatorApi = YandexTranslatorApi(this, translationResult)
////                translatorApi.execute(lanFrom, lanTo)
//            }
//        }
//    }

    private fun onClickCheck() {
//        val task = ClarifaiApiHandler(Uri.fromFile(File(selectedPhotoCardViewModel.getSelectedPhotoCard().value!!.photoFilePathFromStorage))
//                , selectedPhotoCardViewModel.getSelectedPhotoCard().value!!)
//        task.execute()
    }

    private fun onClickFlagButton() {
//        val intent = Intent(requireContext(), ChooseTranslationFlagsFragment::class.java)
//        startActivityForResult(intent, 3)
    }

}