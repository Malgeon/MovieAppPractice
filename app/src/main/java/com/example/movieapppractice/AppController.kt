package com.example.movieapppractice

import android.app.Application
import androidx.multidex.MultiDexApplication
import com.example.movieapppractice.di.repositoryModule
import com.example.movieapppractice.di.retrofitModule
import com.example.movieapppractice.di.viewModelModule
import org.koin.core.context.startKoin

class AppController : MultiDexApplication() {

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