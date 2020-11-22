package ru.movie.app.ui.model

data class MovieDetail(
    val id: Int,
    val title: String,
    val pageScreen: Int,
    val ageRestriction: String,
    val genre: String,
    val rating: Int,
    val reviews: Int,
    val description: String,
    val actors: List<Actor>
)