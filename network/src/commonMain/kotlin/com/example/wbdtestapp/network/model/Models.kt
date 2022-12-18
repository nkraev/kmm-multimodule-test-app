package com.example.wbdtestapp.network.model

import com.example.wbdtestapp.model.Photo
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoResponse(val photos: PhotoPage)

@Serializable
data class PhotoPage(
    val page: Int,
    val pages: Int,
    @SerialName("perpage") val perPage: Int,
    val total: Int,
    val photo: List<Photo>
)