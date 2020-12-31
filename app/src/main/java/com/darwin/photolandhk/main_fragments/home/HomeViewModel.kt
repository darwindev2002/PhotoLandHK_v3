package com.darwin.photolandhk.main_fragments.home

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darwin.photolandhk.network.Api
import com.darwin.photolandhk.network.ApiStatus
import com.darwin.photolandhk.network.categoryList
import com.darwin.photolandhk.network.homeOverviewListSize
import com.darwin.photolandhk.posts.PostContent
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.min

class HomeViewModel(private val skeletonLayout: View, val title: String) : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _posts = MutableLiveData<List<PostContent>>()
    val posts: LiveData<List<PostContent>>
        get() = _posts
    private val _navigateToSelectedPost = MutableLiveData<PostContent>()
    val navigateToSelectedPost: LiveData<PostContent>
        get() = _navigateToSelectedPost

    init {
        getSmallOverview()
    }

    private fun getSmallOverview() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val categoryId = categoryList[title] ?: error("Error: category not found")
                Log.e("ApiGetPostData", "categoryId succesfully got")
                val count = min(Api.retrofitService.getCategoryPostCount(categoryId).count, homeOverviewListSize)
                Log.e("ApiGetPostData", "title=$title, categoryId=$categoryId, count=$count")
                _posts.value = Api.retrofitService.getPostList(per_page =count, category =categoryId)
                Log.e("ApiGetPostData", "title=$title: Api succesfully ran")
                _status.value = ApiStatus.DONE
                skeletonLayout.animate()
                    .translationY(skeletonLayout.height.toFloat())
                    .alpha(0.0f)
                    .setDuration(300)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            skeletonLayout.visibility = View.GONE
                        }
                    })

//                skeletonLayout.visibility = View.GONE
            } catch (e: Exception) {
                e.printStackTrace()
                _status.value = ApiStatus.ERROR
                _posts.value = ArrayList()
            }
        }
    }

    fun displayPostContent(post: PostContent) {
        _navigateToSelectedPost.value = post
    }

}