package com.koding.web.ui.home

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.koding.web.data.local.entity.ArticleEntity
import com.koding.web.data.remote.Repository
import com.koding.web.data.remote.model.Sliders
import com.koding.web.utils.SettingPreferences
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository,
    private val preferences: SettingPreferences
) : ViewModel() {
    private var _sliders = MutableLiveData<List<Sliders>>()
    val slider: LiveData<List<Sliders>> get() = _sliders

    val category = repository.getCategory().cachedIn(viewModelScope)
    val article = repository.getArticle().cachedIn(viewModelScope)

    init {
        getSliders()
    }

    private fun getSliders() {
        // proses asinkronus
        viewModelScope.launch {
            try {
                // get function suspend dari server
                val remote = repository.getSlider().data
                // set data atau update data
                _sliders.postValue(remote)
            } catch (e: Exception) {
                Log.d("TAG", "Error Slider $e")
            }
        }
    }

    fun setBookmark(article: ArticleEntity) {
        viewModelScope.launch {
            val isBookmark = article.isBookmark
            repository.setArticleBookmark(article, !isBookmark)
        }
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return preferences.getThemeSettings().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSettings(isDarkModeActive)
        }
    }

}