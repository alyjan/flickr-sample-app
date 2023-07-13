package com.example.testapplication.view.bas

import com.example.testapplication.view.base.ActionHandler
import com.example.testapplication.view.base.ViewState
import com.example.testapplication.view.base.ViewStateHandler

interface ViewComponent<T: ViewState> : ViewStateHandler<T> {
    var actionHandler: ActionHandler
}