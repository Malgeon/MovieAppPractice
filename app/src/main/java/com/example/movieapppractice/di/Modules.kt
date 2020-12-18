package com.example.movieapppractice.di

import com.example.movieapppractice.data.repository.AppRepository
import com.example.movieapppractice.data.repository.Repository
import com.example.movieapppractice.data.source.remote.Api
import com.example.movieapppractice.ui.home.HomeViewModel
import com.example.movieapppractice.ui.home.MovieItemViewModel
import com.example.movieapppractice.ui.home.RatingViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class Modules {
    var retrofit = module {
        single {
            Retrofit.Builder()
                .baseUrl("http://boostcourse-appapi.connect.or.kr:10000")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        single {
            get<Retrofit>().create(Api::class.java)
        }
    }

    var repository = module {
        factory<Repository> {
            AppRepository(get())
        }
    }

    var viewModel= module {
        viewModel { HomeViewModel(get()) }
        viewModel { MovieItemViewModel(get()) }
        viewModel { RatingViewModel(get()) }
    }
}
