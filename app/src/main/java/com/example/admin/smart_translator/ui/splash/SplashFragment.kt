package com.example.admin.smart_translator.ui.splash

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.admin.smart_translator.R
import com.example.admin.smart_translator.model.Flag
import com.example.admin.smart_translator.model.LanguageEnum
import com.example.admin.smart_translator.view_model.AllFlagsViewModel
import com.example.admin.smart_translator.view_model.UserViewModel

class SplashFragment : Fragment() {

    private val userViewModel: UserViewModel by activityViewModels()
    private val allFlagsViewModel: AllFlagsViewModel by activityViewModels()
    private val splashDisplayLength = 2000

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        (activity as AppCompatActivity).supportActionBar!!.title = ""
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Handler().postDelayed({
            allFlagsViewModel.setAllFlags(ArrayList(LanguageEnum.values().map {
                val flag = Flag()
                flag.isChecked = false
                flag.langCode = it
                flag.flagIconPath = view.resources.getIdentifier("ic_${it}", "mipmap", requireContext().packageName)
                flag
            }))
            userViewModel.loadPhotoCards(requireContext())
            requireActivity().findNavController(R.id.nav_host_fragment_home).navigate(R.id.action_splashFragment_to_homeFragment)
        }, splashDisplayLength.toLong())
    }

}
