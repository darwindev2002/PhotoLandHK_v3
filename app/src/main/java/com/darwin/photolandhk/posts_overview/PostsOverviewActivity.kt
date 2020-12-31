package com.darwin.photolandhk.posts_overview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.darwin.photolandhk.MESSAGE
import com.darwin.photolandhk.R

class PostsOverviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_overview)

        val message = intent.getStringExtra(MESSAGE.FILTER_CATEGORY.value)!!

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_main_container, PostsOverviewFragment(message))
                .commitNow()
        }
    }
}