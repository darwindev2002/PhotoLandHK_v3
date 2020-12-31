package com.darwin.photolandhk.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.darwin.photolandhk.MESSAGE
import com.darwin.photolandhk.R
import com.darwin.photolandhk.databinding.ActivityPostBinding
import com.darwin.photolandhk.posts.PostContent
import com.darwin.photolandhk.posts.wpAPIDateString2Date
import kotlinx.android.synthetic.main.activity_post.view.*

class PostContentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(findViewById(R.id.titleToolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)
        view.btn_back.setOnClickListener { onBackPressed() }

        val selectedPost: PostContent = intent.getParcelableExtra(MESSAGE.POST.value)!!

        binding.lifecycleOwner = this
        val viewModelFactory = PostViewModelFactory(selectedPost)
        binding.viewModel = ViewModelProvider(this, viewModelFactory).get(PostViewModel::class.java)
        binding.datePublished = wpAPIDateString2Date(selectedPost.date)
        binding.isAuthorThumbnail = true
    }
}