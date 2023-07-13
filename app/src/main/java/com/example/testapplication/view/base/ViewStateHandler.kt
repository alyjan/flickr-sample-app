package com.example.testapplication.view.base

interface ViewStateHandler<T: ViewState> {
    fun handleState(viewState: T)
}