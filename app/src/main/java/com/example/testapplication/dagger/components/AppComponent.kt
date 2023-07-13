package com.example.testapplication.dagger.components

import com.example.testapplication.dagger.modules.NetworkModule
import com.example.testapplication.view.MainActivity
import dagger.Component
import javax.inject.Singleton

@Component(modules = [NetworkModule::class])
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}