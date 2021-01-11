package ru.movie.app.data.models

data class Actor(
    override val id: Int,
    val name: String,
    val picture: String
): ItemModel