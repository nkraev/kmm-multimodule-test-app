package com.example.wbdtestapp.repo

import com.example.wbdtestapp.model.Photo
import kotlinx.coroutines.flow.Flow

interface PhotosRepo {
    // TODO: add pagination
    suspend fun getPhotos(query: String): Flow<List<Photo>>
}