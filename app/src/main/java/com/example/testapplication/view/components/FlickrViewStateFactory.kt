package com.example.testapplication.view.components

import com.example.testapplication.data.Photo
import javax.inject.Inject

class FlickrViewStateFactory @Inject constructor() {

    fun loading(currentPage: Int): FlickrState {
        return FlickrState(
            paginateLoading = currentPage > 1,
            photoListComponentState = if (currentPage == 1) ViewPhotoListComponentState.Loading else null
        )
    }

    fun updatePhotos(
        newPhotos: List<Photo>,
        currentPage: Int
    ): FlickrState {
        return FlickrState(
            photoListComponentState = ViewPhotoListComponentState.DisplayPhotos(
                photos = newPhotos,
                resetData = currentPage == 1
            )
        )
    }

    fun error(
        message: String
    ): FlickrState {
        return FlickrState(
            error = message
        )
    }
}