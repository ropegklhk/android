package com.koding.web.viewmodel

import android.content.Context
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.koding.web.data.local.NewsDatabase
import com.koding.web.data.local.NewsDatabase.Repository.Companion.getInstance
import com.koding.web.data.remote.ApiConfig
import com.koding.web.data.remote.Repository
import com.koding.web.data.remote.Repository.Companion.getInstance
import com.koding.web.di.Injection
import com.koding.web.ui.bookmark.BookmarkViewModel
import com.koding.web.ui.categories.CategoryViewModel
import com.koding.web.ui.detail.DetailViewModel
import com.koding.web.ui.home.HomeViewModel
import com.koding.web.ui.search.SearchViewModel
import com.koding.web.utils.SettingPreferences
import com.koding.web.viewmodel.ViewModelFactory.Companion.getInstance

class ViewModelFactory(
    private val repository: Repository,
    private val settingPreferences: SettingPreferences
) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory(
                Injection.provideRepository(context),
                Injection.providePrefDataStore(context)
            )
        }
    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(repository, settingPreferences) as T
            }
            modelClass.isAssignableFrom(CategoryViewModel::class.java) -> {
                CategoryViewModel(repository) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(repository) as T
            }
            modelClass.isAssignableFrom(BookmarkViewModel::class.java) -> {
                BookmarkViewModel(repository) as T
            }
            modelClass.isAssignableFrom(SearchViewModel::class.java) ->{
                SearchViewModel(repository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
    }
}