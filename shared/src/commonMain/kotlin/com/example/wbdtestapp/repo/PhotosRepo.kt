package com.example.wbdtestapp.repo

import com.example.wbdtestapp.model.Photo
import com.example.wbdtestapp.model.PhotoType
import kotlinx.coroutines.flow.Flow

interface PhotosRepo {
    // TODO: add pagination
    suspend fun getPhotos(query: String): Flow<List<Photo>>

    fun getUrl(photo: Photo, type: PhotoType): String

    fun getFullPhoto(photoId: Long): Photo
}