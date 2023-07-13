package com.example.testapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.repository.FlickrRepository
import com.example.testapplication.view.components.FlickrViewStateFactory
import javax.inject.Inject

class PhotoViewModelFactory @Inject constructor(
    private val flickrRepository: FlickrRepository,
    private val flickrViewStateFactory: FlickrViewStateFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhotoViewModel::class.java)) {
            return PhotoViewModel(
                flickrRepository,
                flickrViewStateFactory
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}