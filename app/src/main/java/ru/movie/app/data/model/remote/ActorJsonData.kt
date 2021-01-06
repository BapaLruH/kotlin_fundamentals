package ru.movie.app.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorJsonData(
    val id: Int,
    val name: String,
    @SerialName("profile_path")
    val picture: String
)