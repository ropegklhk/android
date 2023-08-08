package com.koding.web.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.koding.web.data.local.entity.ArticleEntity
import com.koding.web.data.remote.Repository
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: Repository
) : ViewModel() {

    fun searchArticle(search: String) = repository.searchSearchArticle(search).cachedIn(viewModelScope)

    fun setBookmark(article: ArticleEntity) {
        viewModelScope.launch {
            val isBookmark = article.isBookmark
            repository.setArticleBookmark(article, !isBookmark)
        }
    }
}