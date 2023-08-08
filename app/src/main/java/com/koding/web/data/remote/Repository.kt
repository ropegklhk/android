package com.koding.web.data.remote

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.koding.web.data.Resource
import com.koding.web.data.datasource.ArticlePagingSource
import com.koding.web.data.datasource.ArticleRemoteMediator
import com.koding.web.data.datasource.CategoriesPagingSource
import com.koding.web.data.local.entity.ArticleEntity
import com.koding.web.data.local.NewsDatabase
import com.koding.web.data.remote.model.Article
import com.koding.web.data.remote.model.Category
import com.koding.web.data.remote.model.Response
import com.koding.web.data.remote.model.Sliders
import kotlinx.coroutines.flow.Flow

class Repository private constructor(
    private val apiService: ApiService,
    private val database: NewsDatabase
) {

    companion object {
        private var instance: Repository? = null
        fun getInstance(
            apiService: ApiService,
            database: NewsDatabase
        ): Repository = instance ?: synchronized(this)
        {
            instance ?: Repository(apiService, database)
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

    fun getBookmarkArticle(): LiveData<List<ArticleEntity>> =
        database.articleDao().getArticleBookMark()

    suspend fun setArticleBookmark(articleEntity: ArticleEntity, bookmarkState: Boolean) {
        articleEntity.isBookmark = bookmarkState
        database.articleDao().updateArticle(articleEntity)
    }

    fun getArticle(): Flow<PagingData<ArticleEntity>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = pagingConfig,
            remoteMediator = ArticleRemoteMediator(apiService, database),
            pagingSourceFactory = {
                database.articleDao().getArticle()
                //ArticlePagingSource(apiService)
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

    fun searchSearchArticle(search: String): Flow<PagingData<ArticleEntity>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                ArticlePagingSource(apiService, database, search)
            }
        ).flow
    }

    fun getDetailCategory(slug: String): LiveData<Resource<List<ArticleEntity>>> = liveData {
        emit(Resource.Loading)
        try {
            val response = apiService.getDetailCategory(slug)
            val article = response.data.post.map { article ->
                val isBookmarked = database.articleDao().isArticleBookmarked(id = article.id)
                ArticleEntity(
                    id = article.id,
                    image = article.image,
                    title = article.title,
                    content = article.content,
                    category = article.category.name,
                    author = "article.user.name",
                    date = article.createdAt,
                    isBookmark = isBookmarked,
                    slug = article.slug
                )
            }
            emit(Resource.Success(article))

        } catch (e: Exception) {
            emit(Resource.Error(e.toString()))
        }
    }


}