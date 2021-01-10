package com.darwin.photolandhk

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Build.VERSION_CODES.M
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.darwin.photolandhk.post.PostContentActivity
import com.darwin.photolandhk.posts.PostContent
import com.darwin.photolandhk.posts_overview.PostsOverviewActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var fragmentMain: DrawerLayout
    private lateinit var viewPager: ViewPager2

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.titleToolbar))

        val toolbar: Toolbar = findViewById(R.id.titleToolbar)
        setSupportActionBar(toolbar)

        fragmentMain = findViewById(R.id.fragment_main)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)
        val toggle = ActionBarDrawerToggle(
            this,
            fragmentMain,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        val drawerArrowDrawable = DrawerArrowDrawable(this)
        drawerArrowDrawable.color = getColor(R.color.colorText)
        toggle.drawerArrowDrawable = drawerArrowDrawable
        fragmentMain.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setCheckedItem(R.id.nav_home)  // Set to HomeFragment when first opened

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false

        viewPager = findViewById(R.id.main_viewpager)
        viewPager.adapter = MainViewPagerAdapter(this)
        viewPager.offscreenPageLimit = 1
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                bottomNavigationView.selectedItemId = correspondingPage(position)
                super.onPageSelected(position)
                
            }
        })

        val floatingBtn: FloatingActionButton = findViewById(R.id.btn_product_library)
        floatingBtn.setOnClickListener { startProductLibraryActivity() }

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setViewPagerNestScrollEnabled(isEnabled: Boolean){
        viewPager.isNestedScrollingEnabled = isEnabled
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            // BottomNavigationView & Drawer Navigation (Except YouTube -> special case)
            R.id.nav_home -> viewPager.setCurrentItem(0)
            R.id.nav_youtube -> viewPager.setCurrentItem(1)
            R.id.nav_library -> viewPager.setCurrentItem(2)
            R.id.nav_events -> viewPager.setCurrentItem(3)
            R.id.nav_settings -> viewPager.setCurrentItem(4)
            // Drawer Navigation
            R.id.nav_posts_all -> startPostsOverviewActivity(getString(R.string.filter_all))
            R.id.nav_posts_news -> startPostsOverviewActivity(getString(R.string.title_news))
            R.id.nav_posts_report -> startPostsOverviewActivity(getString(R.string.title_report))
            R.id.nav_posts_discussion -> startPostsOverviewActivity(getString(R.string.title_discussion))
            R.id.nav_youtube_latest -> viewPager.setCurrentItem(1)
            R.id.nav_youtube_review -> viewPager.setCurrentItem(1)
            R.id.nav_youtube_broadcasts -> viewPager.setCurrentItem(1)
        }
        fragmentMain.closeDrawer(GravityCompat.START)
        return true
    }

    private fun setCurrentPage(id: Int, youtubeFilter: Int = 0) {
        when (id) {
            R.id.nav_home -> viewPager.setCurrentItem(0)
            R.id.nav_youtube -> viewPager.setCurrentItem(1)
            R.id.nav_library -> viewPager.setCurrentItem(2)
            R.id.nav_events -> viewPager.setCurrentItem(3)
            R.id.nav_settings -> viewPager.setCurrentItem(4)
        }
    }

    private fun correspondingPage(position: Int) : Int {
        return when (position) {
            0 -> R.id.nav_home
            1 -> R.id.nav_youtube
            2 -> R.id.nav_library
            3 -> R.id.nav_events
            4 -> R.id.nav_settings
            else -> R.id.nav_home
        }
    }

    fun startPostsOverviewActivity(title: String) {
        val intent = Intent(this, PostsOverviewActivity::class.java).apply {
            putExtra(MESSAGE.FILTER_CATEGORY.value, title)
        }
        startActivity(intent)
    }

    fun startPostContentActivity(post: PostContent) {
        val intent = Intent(this, PostContentActivity::class.java).apply {
            putExtra(MESSAGE.POST.value, post)
        }
        startActivity(intent)
    }

    fun startProductLibraryActivity() {
        val intent = Intent(this, ProductLibraryActivity::class.java)
        startActivity(intent)
    }

}
