package com.koding.web.di

import com.koding.web.data.remote.ApiConfig
import com.koding.web.data.remote.Repository
object Injection {
    fun provideRepository(): Repository {
        val apiService = ApiConfig.getApiService()
        return Repository.getInstance(apiService)
    }
}

