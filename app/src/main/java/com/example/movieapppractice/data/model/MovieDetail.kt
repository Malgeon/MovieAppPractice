package com.example.movieapppractice.data.model

data class MovieDetail (
    var id: Int,
    var title: String,
    var date: String,
    var user_rating: Float,
    var audience_rating: Float,
    var reviewer_rating: Float,
    var reservation_rate: Float,
    var reservation_grade: Int,
    var grade: Int,
    var thumb: String,
    var image: String,
    var photos: String?,
    var videos: String?,
    var outlinks: String?,
    var genre: String,
    var duration: Int,
    var audience: Int,
    var synopsis: String,
    var director: String,
    var actor: String,
    var like: Int,
    var dislike: Int
)
