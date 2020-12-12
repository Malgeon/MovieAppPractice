package com.example.movieapppractice.data.repository

import com.example.movieapppractice.data.model.BaseResponse
import com.example.movieapppractice.data.model.CommentList
import com.example.movieapppractice.data.model.MovieData
import com.example.movieapppractice.data.model.MovieDetail
import io.reactivex.Single

interface Repository {
    fun getHomeData(type: Int): Single<BaseResponse<MovieData>>
    fun getMovieDetailData(id: Int): Single<BaseResponse<MovieDetail>>
    fun getCommentData(id: Int, startIndex: Int, length: Int): Single<BaseResponse<CommentList>>
}