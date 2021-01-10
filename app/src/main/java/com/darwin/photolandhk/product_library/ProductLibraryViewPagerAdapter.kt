package com.darwin.photolandhk.product_library

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ProductLibraryViewPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    private val mFragmentList = mutableListOf<Fragment>(
        ProductLibraryFragment("camera"),
        ProductLibraryFragment("lens"),
        ProductLibraryFragment("filter"),
        ProductLibraryFragment("tripod"),
        ProductLibraryFragment("camcorder")
    )

    override fun getItemCount(): Int {
        return mFragmentList.size
    }

    override fun createFragment(position: Int): Fragment {
        return mFragmentList[position]
    }
}