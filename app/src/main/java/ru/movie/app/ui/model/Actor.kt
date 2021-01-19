package ru.movie.app.ui.model

data class Actor(
    override val id: Int,
    val name: String,
    val picture: String
): ItemModel