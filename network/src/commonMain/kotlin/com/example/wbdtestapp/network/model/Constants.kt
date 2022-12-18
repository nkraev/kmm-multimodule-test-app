package com.example.wbdtestapp.network.model

import io.ktor.client.request.*

private object Constants {
    const val API_URL = "https://api.flickr.com/services/rest/"
    const val API_KEY = "1508443e49213ff84d566777dc211f2a"
}

object Endpoints {
    fun photos(query: String): HttpRequestBuilder.() -> Unit = {
        request {
            url(Constants.API_URL)
            parameter("method", "flickr.photos.search")
            parameter("api_key", Constants.API_KEY)
            parameter("format", "json")
            parameter("text", query)
        }
    }
}