package com.koding.web.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koding.web.data.local.entity.ArticleEntity
import com.koding.web.data.remote.Repository
import kotlinx.coroutines.launch

class BookmarkViewModel(private val repository: Repository) : ViewModel() {

    fun getBookmarkArticle() = repository.getBookmarkArticle()

    fun setBookmark(articleEntity: ArticleEntity) {
        viewModelScope.launch {
            val isBookmarked = articleEntity.isBookmark
            repository.setArticleBookmark(articleEntity, !isBookmarked)
        }
    }
}