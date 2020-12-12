package com.example.movieapppractice.data.repository

import com.example.movieapppractice.data.model.BaseResponse
import com.example.movieapppractice.data.model.CommentList
import com.example.movieapppractice.data.model.MovieData
import com.example.movieapppractice.data.model.MovieDetail
import com.example.movieapppractice.data.source.remote.Api
import io.reactivex.Single

class AppRepository(private val api : Api) : Repository {

    override fun getHomeData(type: Int): Single<BaseResponse<MovieData>> {
        return api.getMovieData(type)
    }

    override fun getMovieDetailData(movieId: Int): Single<BaseResponse<MovieDetail>> {
        return api.getMovieDetailData(movieId)
    }

    override fun getCommentData(
        id: Int,
        startIndex: Int,
        length: Int
    ): Single<BaseResponse<CommentList>> {
        return api.getComments(id, startIndex, length)
    }


}