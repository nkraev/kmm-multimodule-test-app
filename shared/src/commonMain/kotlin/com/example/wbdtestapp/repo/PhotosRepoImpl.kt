package com.example.wbdtestapp.repo

import com.example.wbdtestapp.model.Photo
import com.example.wbdtestapp.network.FlickrApiService
import io.ktor.client.*

internal class PhotosRepoImpl(httpClient: HttpClient) : PhotosRepo {
    private val apiService = FlickrApiService(httpClient)

    override suspend fun getPhotos(query: String): List<Photo> = apiService.fetchPhotos(query)
}