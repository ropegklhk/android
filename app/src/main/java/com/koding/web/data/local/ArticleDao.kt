package com.koding.web.data.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.*
import com.koding.web.data.local.entity.ArticleEntity

@Dao
interface ArticleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: List<ArticleEntity>)

    @Query("SELECT * FROM tbl_article ORDER BY date DESC")
    fun getArticle(): PagingSource<Int, ArticleEntity>

    @Query("SELECT * FROM tbl_article WHERE isBookmark = 1")
    fun getArticleBookMark(): LiveData<List<ArticleEntity>>

    @Query("DELETE FROM tbl_article WHERE isBookmark = 0")
    suspend fun deleteAll()

    @Update
    suspend fun updateArticle(article: ArticleEntity)

    @Query("SELECT EXISTS(SELECT * FROM tbl_article WHERE id = :id AND isBookmark = 1)")
    suspend fun isArticleBookmarked(id: Int): Boolean

}

