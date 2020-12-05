package com.example.movieapppractice.data.repository

import com.example.movieapppractice.data.model.MovieHome
import com.google.gson.JsonObject
import io.reactivex.Single

interface Repository {
    fun getHomeData(type: Int): Single<MovieHome>
}