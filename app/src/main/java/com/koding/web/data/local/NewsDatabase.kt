package com.koding.web.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.koding.web.data.local.entity.ArticleEntity
import com.koding.web.data.remote.ApiConfig
import com.koding.web.data.remote.ApiService

@Database(
    entities = [ArticleEntity::class, RemoteKeysArticle::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao
    abstract fun remoteKeysArticleDao(): RemoteKeysArticleDao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): NewsDatabase {
            return INSTANCE ?: synchronized(this) {
                val
                        newsDatabase = INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java, "news_database"
                ).fallbackToDestructiveMigration().build().also {
                    INSTANCE = it
                }
                newsDatabase
            }
        }
    }
    class Repository private constructor(
        private val apiService: ApiService,
        private val database: NewsDatabase
    ){
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
        object Injection {
            fun provideRepository(context: Context): Repository {
                val apiService = ApiConfig.getApiService()
                val database = NewsDatabase.getDatabase(context)
                return Repository.getInstance(apiService, database)
            }
        }
    }
}