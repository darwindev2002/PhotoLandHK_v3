package com.darwin.photolandhk.main_fragments.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.darwin.photolandhk.databinding.HomeRecyclerviewItemBinding
import com.darwin.photolandhk.posts.PostContent

class HomeOverviewAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<PostContent, HomeOverviewAdapter.HomePostViewHolder>(
        DiffCallback
    ) {

    class HomePostViewHolder(private var binding: HomeRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PostContent) {
            binding.post = item
            binding.isHomeOverview = true
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<PostContent>() {
        override fun areContentsTheSame(oldItem: PostContent, newItem: PostContent): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: PostContent, newItem: PostContent): Boolean {
            return oldItem === newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomePostViewHolder {
        return HomePostViewHolder(
            HomeRecyclerviewItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: HomePostViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    class OnClickListener(val clickListener: (post: PostContent) -> Unit) {
        fun onClick(post: PostContent) = clickListener(post)
    }

}