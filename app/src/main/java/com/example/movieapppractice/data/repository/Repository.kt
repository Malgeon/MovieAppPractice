package com.example.movieapppractice.data.repository

import com.example.movieapppractice.data.model.*
import io.reactivex.Single

interface Repository {
    fun getHomeData(type: Int): Single<BaseResponse<MovieData>>
    fun getMovieDetailData(id: Int): Single<BaseResponse<MovieDetail>>
    fun getCommentData(id: Int, startIndex: Int, length: Int): Single<BaseResponse<CommentList>>
    fun postLikeOrDislike(id: Int, isLike: String?, isDislike: String?): Single<PostResponse>
    fun postComment(id: Int, writer: String, rating: Float, contents: String): Single<PostResponse>

}