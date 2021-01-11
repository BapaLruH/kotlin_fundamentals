package ru.movie.app.data.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActorsResponse(
    @SerialName("cast") val actors: List<Cast>
) {
    @Serializable
    data class Cast(
        val id: Int,
        val name: String,
        @SerialName("profile_path")
        val picture: String?
    )
}