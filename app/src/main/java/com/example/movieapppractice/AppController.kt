package com.example.movieapppractice

import androidx.multidex.MultiDexApplication
import com.example.movieapppractice.di.Modules
import org.koin.core.context.startKoin


class AppController : MultiDexApplication() {

    private val diModules by lazy {
        Modules()
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            applicationContext
            modules(diModules.retrofit)
            modules(diModules.repository)
            modules(diModules.viewModel)
        }
    }
}