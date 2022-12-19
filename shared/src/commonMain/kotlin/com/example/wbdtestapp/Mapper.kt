package com.example.wbdtestapp

import com.example.wbdtestapp.database.Photos
import com.example.wbdtestapp.model.Photo

object Mapper {
    fun mapPhotoFromDao(photo: Photos) =
        Photo(photo.id, photo.owner, photo.secret, photo.server, photo.title)
}