package com.example.movieapppractice.di

import com.example.movieapppractice.data.source.remote.Api
import io.reactivex.Single
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

var retrofitModule = module {
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