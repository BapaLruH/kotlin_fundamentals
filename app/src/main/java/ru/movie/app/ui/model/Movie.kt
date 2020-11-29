package ru.movie.app.ui.model

data class Movie(
    val id: Int,
    val title: String,
    val pageScreen: Int,
    val ageRestriction: String,
    val genre: String,
    val rating: Int,
    val reviews: Int,
    val like: Boolean,
    val duration: Int
)