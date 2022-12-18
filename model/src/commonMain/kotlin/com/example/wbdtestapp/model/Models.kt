package com.example.wbdtestapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponse(val photos: PhotoPage)

@Serializable
data class PhotoPage(
    val page: Int, val pages: Int, @SerialName("perpage") val perPage: Int, val total: Int, val photo: List<Photo>
)

@Serializable
data class Photo(val id: Long, val owner: String, val secret: String, val server: String)