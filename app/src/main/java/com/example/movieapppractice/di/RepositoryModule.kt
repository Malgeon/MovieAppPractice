package com.example.movieapppractice.di

import com.example.movieapppractice.data.repository.AppRepository
import com.example.movieapppractice.data.repository.Repository
import org.koin.dsl.module

var repositoryModule = module {
    factory<Repository> {
        AppRepository(get())
    }
}