package com.koding.web.ui.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.koding.web.R
import com.koding.web.data.remote.model.Category
import com.koding.web.databinding.ItemCategoryAllBinding

class CategoryAllAdapter(private val onClick: (category: Category) -> Unit) :
    PagingDataAdapter<Category, CategoryAllAdapter.CategoryAllViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: CategoryAllViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAllViewHolder =
        CategoryAllViewHolder(
            ItemCategoryAllBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    inner class CategoryAllViewHolder(private val binding: ItemCategoryAllBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category?) {
            item?.let {
                with(binding) {
                    imCategory.load(it.image) {
                        crossfade(true)
                        crossfade(400)
                        error(R.drawable.ic_broken_image)
                        transformations(CircleCropTransformation())
                    }
                    tvCategory.text = it.name
                    itemView.setOnClickListener {
                        onClick(item)
                    }
                }
            }
        }

    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(
                oldItem: Category,
                newItem: Category
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Category,
                newItem: Category
            ): Boolean = oldItem == newItem

        }
    }
}