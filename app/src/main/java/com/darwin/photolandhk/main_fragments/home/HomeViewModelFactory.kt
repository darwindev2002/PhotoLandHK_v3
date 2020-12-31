package com.darwin.photolandhk.main_fragments.home

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeViewModelFactory(
    private val skeletonLayout: View,
    private val title: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(skeletonLayout, title) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}