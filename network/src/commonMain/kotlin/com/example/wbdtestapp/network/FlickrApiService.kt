package com.example.wbdtestapp.network

import com.example.wbdtestapp.model.Photo
import com.example.wbdtestapp.network.model.Endpoints
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class FlickrApiService(private val httpClient: HttpClient) {
    suspend fun fetchPhotos(query: String): List<Photo> = httpClient.get(Endpoints.photos(query)).body()
}