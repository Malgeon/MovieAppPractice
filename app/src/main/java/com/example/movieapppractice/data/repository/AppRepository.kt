package com.example.movieapppractice.data.repository

import com.example.movieapppractice.data.model.*
import com.example.movieapppractice.data.source.remote.Api
import io.reactivex.Single

class AppRepository(private val api : Api) : Repository {

    override fun getHomeData(type: Int): Single<BaseResponse<MovieData>> {
        return api.getMovieData(type)
    }

    override fun getMovieDetailData(movieId: Int): Single<BaseResponse<MovieDetail>> {
        return api.getMovieDetailData(movieId)
    }

    override fun getCommentData(id: Int, startIndex: Int, length: Int): Single<BaseResponse<CommentList>> {
        return api.getComments(id, startIndex, length)
    }

    override fun postLikeOrDislike(id: Int, isLike: String?, isDislike: String?): Single<PostResponse> {
        return api.postLikeDislike(id, isLike, isDislike)
    }

    override fun postComment(id: Int, writer: String, rating: Float, contents: String): Single<PostResponse> {
        return api.postComment(id, writer, rating, contents)
    }

}