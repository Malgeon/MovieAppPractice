package com.example.movieapppractice.data.source.remote

import com.example.movieapppractice.data.model.BaseResponse
import com.example.movieapppractice.data.model.CommentList
import com.example.movieapppractice.data.model.MovieData
import com.example.movieapppractice.data.model.MovieDetail
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("/movie/readMovieList")
    fun getMovieData(
        @Query("type") type:Int
    ): Single<BaseResponse<MovieData>>

    @GET("/movie/readMovie")
    fun getMovieDetailData(
        @Query("id") id:Int
    ): Single<BaseResponse<MovieDetail>>

    @GET("/movie/readCommentList")
    fun getComments(
        @Query("id") id:Int,
        @Query("startIndex") startIndex:Int,
        @Query("length") length:Int
    ): Single<BaseResponse<CommentList>>


}