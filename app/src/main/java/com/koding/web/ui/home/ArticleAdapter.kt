package com.koding.web.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.koding.web.R
import com.koding.web.data.remote.model.Article
import com.koding.web.databinding.ItemArticleBinding
import com.koding.web.utils.withDateFormat

class ArticleAdapter( private val onClick: (article: Article) -> Unit
) : PagingDataAdapter<Article, ArticleAdapter.ArticleViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val articles = getItem(position)
        holder.bind(articles)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder =
        ArticleViewHolder(
            ItemArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    inner class ArticleViewHolder(val binding: ItemArticleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Article?) {
            item?.let {
                with(binding) {
                    imageArticle.load(it.image) {
                        crossfade(true)
                        crossfade(400)
                        error(R.drawable.ic_broken_image)
                        transformations(RoundedCornersTransformation(25f))
                    }
                    tvTitle.text = it.title
                    tvCategory.text = it.category.name
                    tvAuthorDate.text = it.user.name + " - " + it.createdAt.withDateFormat()
                    // action click pada list article
                    itemView.setOnClickListener {
                        onClick(item)
                    }

                }
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: Article,
                newItem: Article
            ): Boolean =
                oldItem == newItem
        }
    }

}