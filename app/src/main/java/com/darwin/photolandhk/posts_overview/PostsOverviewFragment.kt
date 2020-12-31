package com.darwin.photolandhk.posts_overview

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darwin.photolandhk.MESSAGE
import com.darwin.photolandhk.R
import com.darwin.photolandhk.databinding.FragmentPostsOverviewBinding
import com.darwin.photolandhk.network.ApiFilter
import com.darwin.photolandhk.network.ApiStatus
import com.darwin.photolandhk.post.PostContentActivity
import kotlinx.android.synthetic.main.activity_post.view.*

class PostsOverviewFragment(private val title: String) : Fragment(),
    AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentPostsOverviewBinding

    private lateinit var filterList: Array<String>
    private lateinit var viewModel: PostsOverviewViewModel
    private var selectedCategory = ""

//    private var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.fragment_posts_overview,
            container,
            false
        ) as FragmentPostsOverviewBinding
        binding.lifecycleOwner = this

        val spinner: Spinner = binding.postsFilterSpinner
        ArrayAdapter.createFromResource(
            this.context!!,
            R.array.posts_filter,
            R.layout.posts_overview_filter_spinner
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.adapter = adapter
        }

        filterList = resources.getStringArray(R.array.posts_filter)
        spinner.onItemSelectedListener = this
        spinner.setSelection(filterList.indexOf(title))

        binding.titleToolbar.btn_back.setOnClickListener { activity?.onBackPressed() }

        val swipeLayout = binding.swipeContainer
        swipeLayout.setOnRefreshListener {
            viewModel.getPostsOverview(true, getCorrespondingApiFilter(selectedCategory))
            swipeLayout.isRefreshing = false
        }

        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(
            this,
            PostsOverviewViewModelFactory(binding.progressBar, binding.postsRecyclerview)
        ).get(PostsOverviewViewModel::class.java)


        binding.viewModel = viewModel
        binding.postsRecyclerview.adapter =
            PostsOverviewAdapter(PostsOverviewAdapter.OnClickListener {
                viewModel.displayPostContent(it)
            })
        viewModel.navigateToSelectedPost.observe(viewLifecycleOwner, Observer {
            // Launch Post
            it?.let {
                val intent = Intent(context, PostContentActivity::class.java).apply {
                    putExtra(MESSAGE.POST.value, it)
                }
                startActivity(intent)
            }
        })



        binding.postsRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (viewModel.status.value != ApiStatus.LOADING && linearLayoutManager.findLastCompletelyVisibleItemPosition() == viewModel.gotPosts - 1) {
                    viewModel.getPostsOverview(false, getCorrespondingApiFilter(selectedCategory))
                }
                swipeLayout.isEnabled =
                    linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0
            }
        })

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val newCategory = parent.getItemAtPosition(pos) as String
        if (selectedCategory == "" || selectedCategory != newCategory) {
            viewModel.getPostsOverview(true, getCorrespondingApiFilter(newCategory))
            binding.postsRecyclerview.scrollToPosition(0)
            selectedCategory = newCategory
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {
        TODO("Not yet implemented")
    }

    private fun getCorrespondingApiFilter(title: String): ApiFilter {
        for (filter in ApiFilter.values())
            if (filter.value == title) return filter
        return ApiFilter.SHOW_ALL
    }

}