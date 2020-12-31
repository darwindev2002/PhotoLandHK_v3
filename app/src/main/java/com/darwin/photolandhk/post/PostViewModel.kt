package com.darwin.photolandhk.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darwin.photolandhk.network.Api
import com.darwin.photolandhk.network.ApiStatus
import com.darwin.photolandhk.posts.PostContent
import kotlinx.coroutines.launch

class PostViewModel(post: PostContent) : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _selectedPost = MutableLiveData<PostContent>()
    val selectedPost: LiveData<PostContent>
        get() = _selectedPost
    private val _theAuthorThumbnailURL = MutableLiveData<String>()
    val theAuthorThumbnailURL: LiveData<String>
        get() = _theAuthorThumbnailURL

    init {
        _selectedPost.value = post
        getAuthorThmbnailURL()
    }

    private fun getAuthorThmbnailURL() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                _theAuthorThumbnailURL.value  = Api.retrofitService.getAuthorThumbnail(_selectedPost.value!!.author).avatar_urls.get(THM_RESOLUTION.MEDIUM)
                _status.value = ApiStatus.DONE
            } catch (e: Exception) {
//                e.printStackTrace()
                _status.value = ApiStatus.ERROR
                _theAuthorThumbnailURL.value = ""
            }
        }
    }

    companion object {
        enum class THM_RESOLUTION(val value: Int) {
            LOW(24),
            MEDIUM(48),
            HIGH(96)
            // In fact they turn out to be the same url lmao
        }
    }

}