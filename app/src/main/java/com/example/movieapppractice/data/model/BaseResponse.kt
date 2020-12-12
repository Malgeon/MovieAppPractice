package com.example.movieapppractice.data.model

data class BaseResponse<T> (
    val message: String,
    val code: Int,
    val resultType: String,
    val totalCount: Int?,
    val result: ArrayList<T>
)