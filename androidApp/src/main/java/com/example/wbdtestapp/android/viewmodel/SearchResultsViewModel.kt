package com.example.wbdtestapp.android.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wbdtestapp.android.extensions.handleError
import com.example.wbdtestapp.android.uimodel.UIPhoto
import com.example.wbdtestapp.di.Dependencies
import com.example.wbdtestapp.model.PhotoType
import com.example.wbdtestapp.repo.PhotosRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class SearchResultsViewModel(private val repo: PhotosRepo) : ViewModel() {
    private val _state = MutableLiveData<SearchState>(SearchState.Empty)
    val state: LiveData<SearchState> = _state
    private val inputs = MutableStateFlow("dog")
    private lateinit var job: Job

    init {
        observeInputAndFetchData()
    }

    private fun observeInputAndFetchData() {
        job = viewModelScope.launch {
            inputs.filter { it.isNotEmpty() }.debounce(300)
                .flatMapLatest { query -> repo.getPhotos(query) }
                .handleError { _state.postValue(SearchState.Error("${it.message}")) }
                .collectLatest { photos ->
                    val uiPhotos = photos.map { photo ->
                        UIPhoto(
                            photo.id,
                            repo.getUrl(photo, PhotoType.PREVIEW),
                            photo.title ?: "Unknown"
                        )
                    }
                    _state.postValue(SearchState.SearchReady(uiPhotos))
                }
        }
    }

    fun search(query: String) {
        val request = query.trim()
        if (request.isEmpty()) {
            _state.value = SearchState.Empty
            return
        }

        _state.value = SearchState.Loading
        inputs.tryEmit(request)
        if (job.isCompleted || job.isCompleted) {
            observeInputAndFetchData()
        }
    }


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val appContext = this[APPLICATION_KEY] as Context
                val dependencies = Dependencies(appContext)
                SearchResultsViewModel(dependencies.photosRepo)
            }
        }
    }
}

sealed class SearchState {
    object Empty : SearchState()
    object Loading : SearchState()
    data class SearchReady(val photos: List<UIPhoto>) : SearchState()
    data class Error(val errorMessage: String) : SearchState()
}