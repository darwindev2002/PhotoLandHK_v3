package com.darwin.photolandhk.product_library

import android.view.View
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.darwin.photolandhk.network.*
import kotlinx.coroutines.launch
import kotlin.math.ceil

class ProductLibraryViewModel(private val category: String, private val progressBar: ProgressBar, private val recyclerView: RecyclerView) : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _products = MutableLiveData<MutableList<ProductSimple>>()
    val products: LiveData<MutableList<ProductSimple>>
        get() = _products
    private val _navigateToSelectedProduct = MutableLiveData<ProductSimple>()
    val navigateToSelectedProduct: LiveData<ProductSimple>
        get() = _navigateToSelectedProduct

    var gotProducts = 0
//    private var currentCategory = ApiFilter.SHOW_ALL

    fun getProductsOverview(isInit: Boolean = false, filter: ApiFilter = ApiFilter.SHOW_ALL) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            progressBar.visibility = View.VISIBLE
            try {
                val count = Api.retrofitService.getProductCount()
                if (isInit) {
                    PRODUCT_PERPAGE = Api.retrofitService.getProductPerPage()
                    _products.value =
                        Api.retrofitService.getProductList("category.$category") as MutableList
                    recyclerView.adapter?.notifyDataSetChanged()
                } else if (count > gotProducts) {
                    // Only do request if there are still posts left
                    _products.value?.addAll(
                        Api.retrofitService.getProductList("category.$category", nextPage())
                    )
                    recyclerView.adapter?.notifyDataSetChanged()
                }
                gotProducts = _products.value!!.size
//                currentCategory = filter
                progressBar.visibility = View.GONE
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun displayPostContent(product: ProductSimple) {
        _navigateToSelectedProduct.value = product
    }

    fun displayPostContentComplete() {
        _navigateToSelectedProduct.value = null
    }

    private fun nextPage() : Int = (ceil(gotProducts * 1.0 / LOAD_PER_TIME) + 1).toInt()

}