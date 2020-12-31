package com.darwin.photolandhk.main_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.darwin.photolandhk.R
import com.darwin.photolandhk.databinding.FragmentMainEventsBinding

class EventsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMainEventsBinding>(inflater, R.layout.fragment_main_events, container, false)
        binding.textView2.text = getString(R.string.title_events) + "\nComing soon :)"
        return binding.root
    }
}