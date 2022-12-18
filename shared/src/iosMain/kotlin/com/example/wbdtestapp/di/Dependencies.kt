package com.example.wbdtestapp.di

import com.example.wbdtestapp.database.DatabaseDependencies
import com.example.wbdtestapp.network.FlickrApiService
import com.example.wbdtestapp.repo.PhotosRepo
import com.example.wbdtestapp.repo.PhotosRepoImpl
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Runnable
import platform.darwin.dispatch_async
import platform.darwin.dispatch_get_main_queue
import kotlin.coroutines.CoroutineContext

actual class Dependencies : DatabaseDependencies() {
    actual val photosRepo: PhotosRepo by lazy { PhotosRepoImpl(flickrApiService, databaseQueries, IODispatcher) }
    actual val flickrApiService: FlickrApiService by lazy { FlickrApiService() }

    private object IODispatcher : CoroutineDispatcher() {
        override fun dispatch(context: CoroutineContext, block: Runnable) {
            dispatch_async(dispatch_get_main_queue()) {
                block.run()
            }
        }

    }
}