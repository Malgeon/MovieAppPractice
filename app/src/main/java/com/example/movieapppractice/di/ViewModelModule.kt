package com.example.movieapppractice.di

import com.example.movieapppractice.ui.home.HomeViewModel
import com.example.movieapppractice.ui.home.MovieItemViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

var viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { MovieItemViewModel(get())}
}