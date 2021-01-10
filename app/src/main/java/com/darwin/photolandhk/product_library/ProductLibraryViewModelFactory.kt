package com.darwin.photolandhk.product_library

import android.widget.ProgressBar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

class ProductLibraryViewModelFactory(
    private val category: String,
    private val progressBar: ProgressBar,
    private val recyclerView: RecyclerView
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductLibraryViewModel::class.java)) {
            return ProductLibraryViewModel(category, progressBar, recyclerView) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}