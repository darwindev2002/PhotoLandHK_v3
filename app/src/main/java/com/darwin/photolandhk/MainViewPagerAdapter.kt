package com.darwin.photolandhk

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.darwin.photolandhk.main_fragments.EventsFragment
import com.darwin.photolandhk.main_fragments.LibraryFragment
import com.darwin.photolandhk.main_fragments.SettingsFragment
import com.darwin.photolandhk.main_fragments.YouTubeFragment
import com.darwin.photolandhk.main_fragments.home.HomeFragment

class MainViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    private val mFragmentList = mutableListOf<Fragment>(
        HomeFragment(),
        YouTubeFragment(),
        LibraryFragment(),
        EventsFragment(),
        SettingsFragment()
    )

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }
}