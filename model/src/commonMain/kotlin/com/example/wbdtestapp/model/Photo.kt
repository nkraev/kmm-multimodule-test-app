package com.example.wbdtestapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Photo(val id: String, val owner: String, val secret: String, val server: String)