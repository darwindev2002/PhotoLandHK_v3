package com.darwin.photolandhk.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.darwin.photolandhk.posts.PostContent

class PostViewModelFactory(private val post: PostContent) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(post) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}