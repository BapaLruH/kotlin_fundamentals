package ru.movie.app.ui.model

data class Movie(
    override val id: Int,
    val title: String,
    val overview: String,
    val poster: String,
    val backdrop: String,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int,
    val like: Boolean = false,
    val genres: String,
    val actors: List<Actor>
): ItemModel