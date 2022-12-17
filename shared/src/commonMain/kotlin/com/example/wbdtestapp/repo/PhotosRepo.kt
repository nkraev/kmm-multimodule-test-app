package com.example.wbdtestapp.repo

import com.example.wbdtestapp.model.Photo

interface PhotosRepo {
    // TODO: add pagination
    suspend fun getPhotos(query: String): List<Photo>
}