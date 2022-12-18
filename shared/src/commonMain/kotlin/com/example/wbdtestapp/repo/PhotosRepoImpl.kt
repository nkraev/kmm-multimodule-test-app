package com.example.wbdtestapp.repo

import com.example.wbdtestapp.database.AppDatabaseQueries
import com.example.wbdtestapp.model.Photo
import com.example.wbdtestapp.network.FlickrApiService
import com.squareup.sqldelight.runtime.coroutines.asFlow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class PhotosRepoImpl(
    private val apiService: FlickrApiService,
    private val databaseQueries: AppDatabaseQueries,
    private val ioDispatcher: CoroutineDispatcher
) : PhotosRepo {
    private val imageType = mapOf(
        "preview" to "m", "full" to "b"
    )

    override suspend fun getPhotos(query: String): Flow<List<Photo>> = withContext(ioDispatcher) {
        // async { performApiRequest(query) }

        databaseQueries.queryPhotos(query).asFlow().map { query ->
            val photos = query.executeAsList()
            println(">> Received photos from DB: $photos")
            photos.map { Photo(it.id, it.owner, it.secret, it.server) }
        }
    }

    private suspend fun performApiRequest(query: String) {
        val response = apiService.fetchPhotos(query)
        println(">> Fetched photos from API, starting transaction...")
        databaseQueries.transaction {
            databaseQueries.addRecentSearch(query)
            response.photos.photo.forEach { photo ->
                println(">> Processing photo ${photo.id}...")
                imageType.forEach { (type, sizeSuffix) ->
                    val imageUrl =
                        "https://live.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_$sizeSuffix.jpg"
                    databaseQueries.addImageUrlIntoDb(photo.id, type, imageUrl)
                }
                println(">> Written URLs...")
                databaseQueries.addPhotoIntoDb(photo.id, photo.owner, photo.secret, photo.server, query)
                println(">> Written photo into DB")
            }
        }
        println(">> DB transaction complete!")
    }
}