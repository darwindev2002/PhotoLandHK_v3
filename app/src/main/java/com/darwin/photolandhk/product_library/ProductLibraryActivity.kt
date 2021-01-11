package com.darwin.photolandhk.product_library

//import com.darwin.photolandhk.ui.main.SectionsPagerAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.darwin.photolandhk.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProductLibraryActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_library)
        // TODO: setSupportActionBar

        viewPager = findViewById(R.id.view_pager)
        viewPager.adapter = ProductLibraryViewPagerAdapter(this)
        viewPager.offscreenPageLimit = 1

//        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
//            tab.text = "OBJECT ${position+1}"
            tab.text = when (position){
                0 -> "Camera"
                1 -> "Lens"
                2 -> "Filter"
                3 -> "Tripod"
                4 -> "Camcorder"
                else -> "TBD"
            }
        }.attach()
    }
}