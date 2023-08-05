package com.koding.web.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.koding.web.data.Resource
import com.koding.web.data.datasource.ArticlePagingSource
import com.koding.web.data.datasource.CategoriesPagingSource
import com.koding.web.data.remote.model.Article
import com.koding.web.data.remote.model.Category
import com.koding.web.data.remote.model.Response
import com.koding.web.data.remote.model.Sliders
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    private val apiService: ApiService
) {

    companion object {
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService
        ): Repository = instance ?: synchronized(this)
        {
            instance ?: Repository(apiService)
        }
    }

    suspend fun getSlider(): Response<List<Sliders>> = apiService.getSlider()

    // config paging
    private val pagingConfig = PagingConfig(
        pageSize = 5
    )

    fun getCategory(): Flow<PagingData<Category>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                CategoriesPagingSource(apiService)
            }

        ).flow
    }

    fun getArticle(): Flow<PagingData<Article>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                ArticlePagingSource(apiService)
            }

        ).flow
    }

    fun getDetailArticle(slug: String): LiveData<Resource<Article>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getDetailArticle(slug)
            emit(Resource.Success(response.data))

        } catch (e: Exception) {
            emit(Resource.Error(e.toString()))
        }
    }

    fun getDetailCategory(slug: String): LiveData<Resource<Category>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getDetailCategory(slug)
            emit(Resource.Success(response.data))

        } catch (e: Exception) {
            emit(Resource.Error(e.toString()))
        }
    }

}