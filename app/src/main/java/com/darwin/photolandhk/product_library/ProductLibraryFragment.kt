package com.darwin.photolandhk.product_library

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.darwin.photolandhk.R
import com.darwin.photolandhk.databinding.FragmentProductLibraryBinding
import com.darwin.photolandhk.network.ApiStatus

class ProductLibraryFragment(private val category: String) : Fragment() {

    private lateinit var binding: FragmentProductLibraryBinding
    private lateinit var viewModel: ProductLibraryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate<ViewDataBinding>(
            inflater,
            R.layout.fragment_product_library,
            container,
            false
        ) as FragmentProductLibraryBinding
        binding.lifecycleOwner = this

        // TODO: setup toolbar
//        binding.titleToolbar.btn_back.setOnClickListener { activity?.onBackPressed() }

        val swipeLayout = binding.swipeContainer
        swipeLayout.setOnRefreshListener {
            viewModel.getProductsOverview(true)
            swipeLayout.isRefreshing = false
        }

        viewModel = ViewModelProvider(
            this,
            ProductLibraryViewModelFactory(category, binding.progressBar, binding.productRecyclerview)
        ).get(ProductLibraryViewModel::class.java)


        binding.viewModel = viewModel
        binding.productRecyclerview.adapter =
            ProductAdapter(ProductAdapter.OnClickListener {
                viewModel.displayPostContent(it)
            })
//        viewModel.navigateToSelectedPost.observe(viewLifecycleOwner, Observer {
//            // Launch Post
//            it?.let {
//                val intent = Intent(context, PostContentActivity::class.java).apply {
//                    putExtra(MESSAGE.POST.value, it)
//                }
//                startActivity(intent)
//            }
//        })

        binding.productRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (viewModel.status.value != ApiStatus.LOADING && linearLayoutManager.findLastCompletelyVisibleItemPosition() == viewModel.gotProducts - 1) {
                    viewModel.getProductsOverview(false)
                }
                swipeLayout.isEnabled =
                    linearLayoutManager.findFirstCompletelyVisibleItemPosition() == 0
            }
        })

        viewModel.getProductsOverview(true)

        setHasOptionsMenu(true)
        return binding.root
    }

//    companion object {
//        /**
//         * The fragment argument representing the section number for this
//         * fragment.
//         */
//        private const val ARG_SECTION_NUMBER = "section_number"
//
//        /**
//         * Returns a new instance of this fragment for the given section
//         * number.
//         */
//        @JvmStatic
//        fun newInstance(sectionNumber: Int): ProductLibraryFragment {
//            return ProductLibraryFragment("lens").apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_SECTION_NUMBER, sectionNumber)
//                }
//            }
//        }
//    }

}