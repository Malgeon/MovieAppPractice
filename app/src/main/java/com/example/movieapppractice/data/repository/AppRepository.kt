package com.example.movieapppractice.data.repository

import com.example.movieapppractice.data.model.MovieHome
import com.example.movieapppractice.data.source.remote.Api
import io.reactivex.Single

class AppRepository(private val api : Api) : Repository {

    override fun getHomeData(type: Int): Single<MovieHome> {
        return api.getMovieData(type)
    }
}