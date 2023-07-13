package com.example.testapplication.dagger.modules

import com.example.testapplication.Constants
import com.example.testapplication.api.FlickrApi
import com.example.testapplication.repository.FlickrRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFlickrApi(retrofit: Retrofit): FlickrApi {
        return retrofit.create(FlickrApi::class.java)
    }

    @Provides
    @Singleton
    fun provideFlickrRepository(flickrApi: FlickrApi): FlickrRepository {
        return FlickrRepository(flickrApi)
    }
}