package com.example.wbdtestapp.di

import com.example.wbdtestapp.repo.PhotosRepo
import com.example.wbdtestapp.repo.PhotosRepoImpl
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

private val httpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            useAlternativeNames = false
        })
    }
}

val photosRepo: PhotosRepo = PhotosRepoImpl(httpClient)