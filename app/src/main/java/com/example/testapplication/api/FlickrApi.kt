package com.example.testapplication.api

import com.example.testapplication.data.FlickrResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {

    @GET("services/rest/?method=flickr.photos.getRecent&api_key=a0222db495999c951dc33702500fdc4d&format=json&nojsoncallback=1")
    suspend fun getRecentPhotos(
        @Query("page") page: Int = 1
    ): Response<FlickrResponse>

    @GET("services/rest/?method=flickr.photos.search&api_key=a0222db495999c951dc33702500fdc4d&format=json&nojsoncallback=1")
    suspend fun searchPhotos(
        @Query("text") query: String,
        @Query("page") page: Int = 1
    ): Response<FlickrResponse>
}