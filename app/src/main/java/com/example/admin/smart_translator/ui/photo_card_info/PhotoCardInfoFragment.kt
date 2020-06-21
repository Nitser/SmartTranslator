package com.example.admin.smart_translator.ui.photo_card_info

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.admin.smart_translator.entities.PhotoCard
import com.example.admin.smart_translator.databinding.FragmentPhotoCardInfoBinding
import com.example.admin.smart_translator.ui.photo_card_info.list.TranslationResultListAdapter
import com.example.admin.smart_translator.view_model.CurrentPhotoCardViewModel
import com.example.admin.smart_translator.view_model.UserViewModel

class PhotoCardInfoFragment : Fragment() {

    private lateinit var binding: FragmentPhotoCardInfoBinding
    private lateinit var adapter: TranslationResultListAdapter
    private val userViewModel: UserViewModel by activityViewModels()
    private val currentPhotoCardViewModel: CurrentPhotoCardViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPhotoCardInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        currentPhotoCardViewModel.getCurrentPhotoCards().observe(viewLifecycleOwner, Observer<PhotoCard> { item ->
            adapter.fromData.clear()
            adapter.toData.clear()
            adapter.fromData.addAll(item.resultFrom)
            adapter.toData.addAll(item.resultTo)
            adapter.notifyDataSetChanged()

            setFlagPhotos()
        })
        onClickCheck()
    }

    private fun init() {
        with(currentPhotoCardViewModel.getCurrentPhotoCards().value!!) {
            setFlagPhotos()
            binding.photoCardInfoLayoutFlagList.setOnClickListener { onClickFlagButton() }

            binding.photoCardInfoPhoto.setImageBitmap(bitmap)

            val layoutManager = LinearLayoutManager(requireContext())
            adapter = TranslationResultListAdapter()
            binding.photoCardInfoResultList.layoutManager = layoutManager
            binding.photoCardInfoResultList.adapter = adapter
            binding.photoCardInfoResultList.setHasFixedSize(true)
        }
    }

    private fun setFlagPhotos() {
        with(currentPhotoCardViewModel.getCurrentPhotoCards().value!!) {
            binding.photoCardInfoFlagFrom.setBackgroundResource(flagFromRes)
            binding.photoCardInfoFlagTo.setBackgroundResource(flagToRes)
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
//        if (requestCode == 3) {
//            with(currentPhotoCardViewModel.getCurrentPhotoCards().value!!) {
////                lanFrom = intent!!.getStringExtra("from")
////                lanTo = intent.getStringExtra("to")
//                val fromId = resources.getIdentifier("ic_$lanFrom", "mipmap", requireContext().packageName)
//                val toId = resources.getIdentifier("ic_$lanTo", "mipmap", requireContext().packageName)
//
//                binding.photoCardInfoFlagFrom.setBackgroundResource(fromId)
//                binding.photoCardInfoFlagTo.setBackgroundResource(toId)
//
//                if (resultFrom.isNotEmpty()) {
//                    resultFrom.clear()
//                    resultTo.clear()
//                    binding.photoCardInfoResultList.adapter!!.notifyDataSetChanged()
//                }
////                val translatorApi = YandexTranslatorApi(this, neoResult)
////                translatorApi.execute(lanFrom, lanTo)
//            }
//        }
//    }

    private fun onClickCheck() {
//        val task = ClarifaiApi(Uri.fromFile(File(currentPhotoCardViewModel.getCurrentPhotoCards().value!!.filePath))
//                , currentPhotoCardViewModel.getCurrentPhotoCards().value!!)
//        task.execute()
    }

    private fun onClickFlagButton() {
//        val intent = Intent(requireContext(), ChangeTranslatedFlagsFragment::class.java)
//        startActivityForResult(intent, 3)
    }

}