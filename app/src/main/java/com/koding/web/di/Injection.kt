package com.koding.web.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.koding.web.data.local.NewsDatabase
import com.koding.web.data.remote.ApiConfig
import com.koding.web.data.remote.Repository
import com.koding.web.utils.SettingPreferences

object Injection {
    fun provideRepository(context: Context): Repository {
        val apiService = ApiConfig.getApiService()
        val database = NewsDatabase.getDatabase(context)
        return Repository.getInstance(apiService, database)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    fun providePrefDataStore(context: Context): SettingPreferences {
        return SettingPreferences.getInstance(dataStore = context.dataStore)
    }
}