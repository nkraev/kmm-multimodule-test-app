package com.example.wbdtestapp.di

import com.example.wbdtestapp.database.DatabaseDependencies
import com.example.wbdtestapp.network.FlickrApiService
import com.example.wbdtestapp.repo.PhotosRepo
import com.example.wbdtestapp.repo.PhotosRepoImpl

actual class Dependencies : DatabaseDependencies() {
    actual val photosRepo: PhotosRepo by lazy { PhotosRepoImpl(flickrApiService) }
    actual val flickrApiService: FlickrApiService by lazy { FlickrApiService() }
}