package com.example.wbdtestapp.android.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.wbdtestapp.android.uimodel.UIPhoto
import com.example.wbdtestapp.di.Dependencies
import com.example.wbdtestapp.model.PhotoType
import com.example.wbdtestapp.repo.PhotosRepo

class DetailsViewModel(private val repo: PhotosRepo) : ViewModel() {
    private val _state = MutableLiveData<DetailsState>(DetailsState.Loading)
    val state: LiveData<DetailsState> = _state

    fun getUrl(imageId: Long) {
        val photo = repo.getFullPhoto(imageId)
        _state.value = DetailsState.ImageReady(
            UIPhoto(
                imageId, repo.getUrl(photo, PhotoType.FULL), photo.title ?: "Unknown"
            )
        )
    }


    companion object {
        val Factory = viewModelFactory {
            initializer {
                val appContext = this[APPLICATION_KEY] as Context
                val dependencies = Dependencies(appContext)
                DetailsViewModel(dependencies.photosRepo)
            }
        }
    }
}

sealed class DetailsState {
    object Loading : DetailsState()
    data class ImageReady(val photo: UIPhoto) : DetailsState()
}