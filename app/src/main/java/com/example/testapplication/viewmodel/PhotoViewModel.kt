package com.example.testapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testapplication.adapters.PhotoAction
import com.example.testapplication.data.Photo
import com.example.testapplication.repository.FlickrRepository
import com.example.testapplication.repository.FlickrResult
import com.example.testapplication.view.base.Action
import com.example.testapplication.view.base.ActionHandler
import com.example.testapplication.view.components.FlickrState
import com.example.testapplication.view.components.FlickrViewStateFactory
import com.example.testapplication.view.components.ViewPhotoSearchComponentAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PhotoViewModel constructor(
    private val flickrRepository: FlickrRepository,
    private val flickrViewStateFactory: FlickrViewStateFactory
) : ViewModel(), ActionHandler {

    private val _uiState: MutableStateFlow<FlickrState> = MutableStateFlow(FlickrState())
    val uiState: StateFlow<FlickrState> = _uiState

    private var currentPage = 1
    private var searchTerm: String? = null

    init {
        fetchRecentPhotos()
    }

    private fun fetchRecentPhotos() = viewModelScope.launch {
        _uiState.emit(flickrViewStateFactory.loading(currentPage))
        if (searchTerm.isNullOrEmpty()) {
            currentPage = 1
            searchTerm = null
        }
        emitResult(flickrRepository.getRecentPhotos(currentPage))
    }

    private fun searchPhotos(query: String) = viewModelScope.launch {
        _uiState.emit(flickrViewStateFactory.loading(currentPage))
        if (query != searchTerm) {
            currentPage = 1
            searchTerm = query
        }
        if (searchTerm.isNullOrEmpty()) {
            fetchRecentPhotos()
        } else {
            emitResult(flickrRepository.searchPhotos(query, currentPage))
        }
    }

    private fun emitResult(result: FlickrResult<List<Photo>>) = viewModelScope.launch {
        when (result) {
            is FlickrResult.Success -> {
                _uiState.emit(flickrViewStateFactory.updatePhotos(result.data, currentPage))
                currentPage++
            }
            is FlickrResult.Error -> {
                _uiState.emit(flickrViewStateFactory.error(result.exception.message ?: "There was an error with your request"))
            }
        }
    }

    override fun handleAction(action: Action) {
        when (action) {
            is PhotoAction.FetchNextPage -> {
                searchTerm?.let { searchPhotos(it) } ?: fetchRecentPhotos()
            }
            is ViewPhotoSearchComponentAction.Search -> {
                searchPhotos(action.query)
            }
        }
    }
}