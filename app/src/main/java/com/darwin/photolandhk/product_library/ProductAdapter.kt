package com.darwin.photolandhk.product_library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.darwin.photolandhk.databinding.ProductOverviewCardBinding

class ProductAdapter(private val onClickListener: OnClickListener) : ListAdapter<ProductSimple, ProductAdapter.ProductViewHolder>(
    DiffCallback
) {

    class ProductViewHolder(private var binding: ProductOverviewCardBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductSimple) {
            binding.product = item
            binding.thm = "https://photolandhk.com/wp-content/uploads/2021/01/Samyang-XP-14mm-2.4-300x300.png"
            binding.isProduct = true
        }
    }
//
//    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)
//    }

    companion object DiffCallback : DiffUtil.ItemCallback<ProductSimple>() {
        override fun areContentsTheSame(oldItem: ProductSimple, newItem: ProductSimple): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areItemsTheSame(oldItem: ProductSimple, newItem: ProductSimple): Boolean {
//            return oldItem === newItem
            return true
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ProductOverviewCardBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
        holder.bind(item)
    }

    class OnClickListener(val clickListener: (product: ProductSimple) -> Unit) {
        fun onClick(product: ProductSimple) = clickListener(product)
    }

}