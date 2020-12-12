package com.example.movieapppractice.data.model

data class MovieData (
    var id: Int,
    var title: String,
    var title_eng: String,
    var date: String,
    var user_rating: Float,
    var audience_rating: Float,
    var reviewer_rating: Float,
    var reservation_rate: Float,
    var reservation_grade: Int,
    var grade: Int,
    var thumb: String,
    var image: String
)