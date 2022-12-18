package com.example.wbdtestapp.di

import android.content.Context
import com.example.wbdtestapp.database.DatabaseDependencies
import com.example.wbdtestapp.network.FlickrApiService
import com.example.wbdtestapp.repo.PhotosRepo
import com.example.wbdtestapp.repo.PhotosRepoImpl
import kotlinx.coroutines.Dispatchers

actual class Dependencies(override val context: Context) : DatabaseDependencies() {
    actual val flickrApiService by lazy { FlickrApiService() }
    actual val photosRepo: PhotosRepo by lazy { PhotosRepoImpl(flickrApiService, databaseQueries, Dispatchers.IO) }
}