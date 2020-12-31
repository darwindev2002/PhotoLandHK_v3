package com.darwin.photolandhk.posts_overview

import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.darwin.photolandhk.network.*
import com.darwin.photolandhk.posts.PostContent
import kotlinx.coroutines.launch
import kotlin.math.ceil

class PostsOverviewViewModel(private val progressBar: ProgressBar, private val recyclerView: RecyclerView) : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _posts = MutableLiveData<MutableList<PostContent>>()
    val posts: LiveData<MutableList<PostContent>>
        get() = _posts
    private val _navigateToSelectedPost = MutableLiveData<PostContent>()
    val navigateToSelectedPost: LiveData<PostContent>
        get() = _navigateToSelectedPost

    var gotPosts = 0
    private var currentCategory = ApiFilter.SHOW_ALL

    fun getPostsOverview(isInit: Boolean = false, filter: ApiFilter = ApiFilter.SHOW_ALL) {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            progressBar.visibility = VISIBLE
            try {
                val count = PostCountCallable.getCount(categoryList[filter.value])
                println("-----$count-----${filter.value}-----$gotPosts")
                if (isInit) {
                    _posts.value =
                        if (filter == ApiFilter.SHOW_ALL) Api.retrofitService.getAllPostList() as MutableList
                        else Api.retrofitService.getPostList(category = categoryList[filter.value]!!) as MutableList
                    recyclerView.adapter?.notifyDataSetChanged()
                } else if (count > gotPosts) {
                    // Only do request if there are still posts left
                    println("-----${nextPage()}")
                    _posts.value?.addAll(
                        if (filter == ApiFilter.SHOW_ALL) Api.retrofitService.getAllPostList(page = nextPage())
                        else Api.retrofitService.getPostList(page = nextPage(), category = categoryList[filter.value]!!)
                    )
                    recyclerView.adapter?.notifyDataSetChanged()
                }
                gotPosts = _posts.value!!.size
                currentCategory = filter
                progressBar.visibility = GONE
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = ApiStatus.ERROR
            }
        }
    }

    fun displayPostContent(post: PostContent) {
        _navigateToSelectedPost.value = post
    }

    fun displayPostContentComplete() {
        _navigateToSelectedPost.value = null
    }

    private fun nextPage() : Int = (ceil(gotPosts * 1.0 / LOAD_PER_TIME) + 1).toInt()

}