package com.example.wbdtestapp.repo

import com.example.wbdtestapp.model.Photo
import com.example.wbdtestapp.network.FlickrApiService

internal class PhotosRepoImpl : PhotosRepo {
    private val apiService = FlickrApiService()

    override suspend fun getPhotos(query: String): List<Photo> =
        apiService.fetchPhotos(query).photos.photo
}