package com.darwin.photolandhk.main_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.darwin.photolandhk.R
import com.darwin.photolandhk.databinding.FragmentMainSettingsBinding

class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMainSettingsBinding>(inflater, R.layout.fragment_main_settings, container, false)
//        binding.textView2.text = getString(R.string.title_settings) + "Coming soon :)"
        return binding.root
    }
}