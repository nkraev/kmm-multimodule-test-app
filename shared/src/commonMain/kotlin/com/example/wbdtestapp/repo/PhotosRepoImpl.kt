package com.example.wbdtestapp.repo

import com.example.wbdtestapp.model.Photo
import com.example.wbdtestapp.network.FlickrApiService

internal class PhotosRepoImpl(private val apiService: FlickrApiService) : PhotosRepo {

    override suspend fun getPhotos(query: String): List<Photo> = apiService.fetchPhotos(query).photos.photo
}