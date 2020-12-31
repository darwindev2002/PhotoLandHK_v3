package com.darwin.photolandhk.posts_overview

import android.widget.ProgressBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class PostsOverviewViewModelFactory(
    private val progressBar: ProgressBar,
    private val recyclerView: RecyclerView
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostsOverviewViewModel::class.java)) {
            return PostsOverviewViewModel(progressBar, recyclerView) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}