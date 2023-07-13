package com.example.testapplication.view.components

import com.example.testapplication.view.base.ViewState

data class FlickrState(
    val error: String? = null,
    val paginateLoading: Boolean = false,
    val photoListComponentState: ViewPhotoListComponentState? = null
): ViewState