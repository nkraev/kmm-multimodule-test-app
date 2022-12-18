package com.example.wbdtestapp.network

import com.example.wbdtestapp.model.PhotoResponse
import com.example.wbdtestapp.network.model.Endpoints
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class FlickrApiService {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }

    suspend fun fetchPhotos(query: String): PhotoResponse = httpClient.get(Endpoints.photos(query)).body()
}
