package com.example.movieapppractice.data.source.remote

import com.example.movieapppractice.data.model.*
import io.reactivex.Single
import retrofit2.http.*

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

    @FormUrlEncoded
    @POST("/movie/increaseLikeDisLike")
    fun postLikeDislike(
        @Field("id") id:Int,
        @Field("likeyn") likeyn:String?,
        @Field("dislikeyn") dislikeyn:String?
    ): Single<PostResponse>

    @FormUrlEncoded
    @POST("/movie/createComment")
    fun postComment(
        @Field("id") id:Int,
        @Field("writer") writer:String,
        @Field("rating") rating:Float,
        @Field("contents") contents:String
    ): Single<PostResponse>
}