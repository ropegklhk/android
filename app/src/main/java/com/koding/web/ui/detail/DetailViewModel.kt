package com.koding.web.ui.detail

import androidx.lifecycle.ViewModel
import com.koding.web.data.remote.Repository

class DetailViewModel(private val repository: Repository) : ViewModel() {
    fun getDetailArticle(slug: String) = repository.getDetailArticle(slug)

    fun getDetailCategory(slug: String) = repository.getDetailCategory(slug)
}

