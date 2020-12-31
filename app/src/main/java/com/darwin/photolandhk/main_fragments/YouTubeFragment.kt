package com.darwin.photolandhk.main_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.darwin.photolandhk.R
import com.darwin.photolandhk.databinding.FragmentMainYoutubeBinding

class YouTubeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMainYoutubeBinding>(inflater, R.layout.fragment_main_youtube, container, false)
        binding.textView2.text = getString(R.string.title_youtube) + "\nComing soon :)"
        return binding.root
    }
}