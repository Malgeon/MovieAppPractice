package com.example.movieapppractice.data.source.remote

import com.example.movieapppractice.data.model.MovieHome
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/movie/readMovieList")
    fun getMovieData(
        @Query("type") type:Int
    ): Single<MovieHome>


}