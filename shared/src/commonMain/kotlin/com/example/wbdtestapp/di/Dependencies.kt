package com.example.wbdtestapp.di

import com.example.wbdtestapp.repo.PhotosRepo
import com.example.wbdtestapp.repo.PhotosRepoImpl

class Dependencies {
    val photosRepo: PhotosRepo = PhotosRepoImpl()
}