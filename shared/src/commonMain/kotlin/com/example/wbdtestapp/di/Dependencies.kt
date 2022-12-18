package com.example.wbdtestapp.di

import com.example.wbdtestapp.database.DatabaseDependencies
import com.example.wbdtestapp.network.FlickrApiService
import com.example.wbdtestapp.repo.PhotosRepo

expect class Dependencies : DatabaseDependencies {
    // TODO: find a way to eliminate same dependencies
    val flickrApiService: FlickrApiService
    val photosRepo: PhotosRepo
}