package com.example.movieapppractice

import android.app.Application
import com.example.movieapppractice.di.repositoryModule
import com.example.movieapppractice.di.retrofitModule
import com.example.movieapppractice.di.viewModelModule
import org.koin.core.context.startKoin

class AppController : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            applicationContext
            modules(retrofitModule)
            modules(repositoryModule)
            modules(viewModelModule)
        }
    }
}