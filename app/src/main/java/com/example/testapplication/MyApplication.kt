package com.example.testapplication

import android.app.Application
import com.example.testapplication.dagger.components.AppComponent
import com.example.testapplication.dagger.components.DaggerAppComponent

class MyApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.create()
    }
}