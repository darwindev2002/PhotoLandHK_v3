package com.darwin.photolandhk.main_fragments.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darwin.photolandhk.MainActivity
import com.darwin.photolandhk.R
import com.darwin.photolandhk.databinding.FragmentHomeBinding
import com.darwin.photolandhk.databinding.HomeRecyclerviewCardBinding

class HomeFragment : Fragment() {

    private lateinit var newsViewModel: HomeViewModel
    private lateinit var reportViewModel: HomeViewModel
    private lateinit var discussionViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.fragment_home,
            container,
            false
        ) as FragmentHomeBinding
        binding.adHeader.setOnClickListener {
            startBrowserLaowa(it)
        }
        binding.lifecycleOwner = this

        newsViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(binding.homeNewsCard.skeletonList, getString(R.string.title_news))
        ).get("news", HomeViewModel::class.java)
        createRecyclerView(binding.homeNewsCard, newsViewModel)

        reportViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(
                binding.homeReportCard.skeletonList,
                getString(R.string.title_report)
            )
        ).get("report", HomeViewModel::class.java)
        createRecyclerView(binding.homeReportCard, reportViewModel)

        discussionViewModel = ViewModelProvider(
            this,
            HomeViewModelFactory(
                binding.homeDiscussionCard.skeletonList,
                getString(R.string.title_discussion)
            )
        ).get("discussion", HomeViewModel::class.java)
        createRecyclerView(binding.homeDiscussionCard, discussionViewModel)

        return binding.root
    }

    private fun createRecyclerView(
        viewBinding: HomeRecyclerviewCardBinding,
        viewModel: HomeViewModel
    ) {
        viewBinding.skeletonList.visibility = View.VISIBLE
        viewBinding.skeletonList.bringToFront()
        viewBinding.viewModel = viewModel
        viewModel.navigateToSelectedPost.observe(viewLifecycleOwner, Observer {
            it?.let {
                (activity as MainActivity).startPostContentActivity(it)
            }
        })

        val rV = viewBinding.homeRecyclerview
        rV.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rV.adapter = HomeOverviewAdapter(HomeOverviewAdapter.OnClickListener {
            viewModel.displayPostContent(it)
        })
        val viewMore: Button = viewBinding.buttonMore
        viewMore.setOnClickListener {
            (activity as MainActivity).startPostsOverviewActivity(viewModel.title)
        }

        rV.addOnItemTouchListener(object :
            RecyclerView.OnItemTouchListener {
            private var x1: Float = 0F
            private var justReturnedFromEnd = false
            private var onSecondScrollAfterReturn = false
            override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
                val action = e.action
                return if (rV.canScrollHorizontally(RecyclerView.FOCUS_FORWARD) || (justReturnedFromEnd && e.x-x1>=0) || onSecondScrollAfterReturn) {
                    // In the middle of the recyclerView
                    if (onSecondScrollAfterReturn) onSecondScrollAfterReturn = false
                    when (action) {
                        MotionEvent.ACTION_MOVE -> {
                            justReturnedFromEnd = false
                            onSecondScrollAfterReturn = true
                            rv.parent.requestDisallowInterceptTouchEvent(true)
                        }
                    }
                    false
                } else {  // At the end of the recyclerView
                    when (action) {
                        MotionEvent.ACTION_MOVE -> rv.parent.requestDisallowInterceptTouchEvent(false)
                        MotionEvent.ACTION_DOWN -> {
                            justReturnedFromEnd = true
                            x1=e.x
                            rv.parent.requestDisallowInterceptTouchEvent(true)
                            return false
                        }
                    }
                    true
                }
            }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}
        })

    }

    private fun startBrowserLaowa(view: View) {
        val url = "https://www.laowa.com.hk"
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

}