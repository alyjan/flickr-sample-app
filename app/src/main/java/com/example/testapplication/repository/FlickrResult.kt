package com.example.testapplication.repository

sealed class FlickrResult<out T> {
    data class Success<out T>(val data: T): FlickrResult<T>()
    data class Error(val exception: Exception): FlickrResult<Nothing>()
}