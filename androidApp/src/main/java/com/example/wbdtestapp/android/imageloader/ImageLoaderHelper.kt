package com.example.wbdtestapp.android.imageloader

import android.content.Context
import coil.ImageLoader
import coil.disk.DiskCache
import coil.memory.MemoryCache

object ImageLoaderHelper {
    private lateinit var imageLoader: ImageLoader
    fun get(context: Context): ImageLoader {
        if (!::imageLoader.isInitialized) {
            imageLoader = ImageLoader.Builder(context).memoryCache {
                MemoryCache.Builder(context).maxSizePercent(0.25).weakReferencesEnabled(true)
                    .build()
            }.diskCache {
                DiskCache.Builder().maxSizePercent(0.1).build()
            }.build()
        }

        return imageLoader
    }
}