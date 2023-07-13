package com.example.testapplication.repository

import com.example.testapplication.api.FlickrApi
import com.example.testapplication.data.Photo
import javax.inject.Inject

class FlickrRepository @Inject constructor(private val flickrApi: FlickrApi) {

    suspend fun getRecentPhotos(page: Int): FlickrResult<List<Photo>> = try {
        val response = flickrApi.getRecentPhotos(page = page)
        FlickrResult.Success(response.body()?.photos?.photo ?: emptyList())
    } catch (e: Exception) {
        FlickrResult.Error(e)
    }

    suspend fun searchPhotos(query: String, page: Int): FlickrResult<List<Photo>> = try {
        val response = flickrApi.searchPhotos(query = query, page = page)
        FlickrResult.Success(response.body()?.photos?.photo ?: emptyList())
    } catch (e: Exception) {
        FlickrResult.Error(e)
    }
}
