package ru.movie.app.data.model.remote

import kotlinx.serialization.Serializable

@Serializable
data class GenreJsonData(
    val id: Int,
    val name: String
)
