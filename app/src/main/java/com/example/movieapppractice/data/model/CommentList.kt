package com.example.movieapppractice.data.model

data class CommentList (
    var id: Int,
    var writer: String,
    var movie_id: Int,
    var writer_image: String,
    var time: String,
    var timestamp: Long,
    var rating: Float,
    var contents: String,
    var recommend: Int
)