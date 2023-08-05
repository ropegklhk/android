package com.koding.web.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.koding.web.data.remote.Repository
import com.koding.web.data.remote.model.Category
import kotlinx.coroutines.flow.Flow

class CategoryViewModel(private val repository: com.koding.web.data.remote.Repository) : ViewModel() {
    val getCategory: Flow<PagingData<Category>> =
        repository.getCategory().cachedIn(viewModelScope)
}