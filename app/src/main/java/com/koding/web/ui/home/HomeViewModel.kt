package com.koding.web.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.koding.web.data.remote.Repository
import com.koding.web.data.remote.model.Sliders
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: Repository
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

}