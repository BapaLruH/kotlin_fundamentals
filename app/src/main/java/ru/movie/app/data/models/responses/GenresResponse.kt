package ru.movie.app.data.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GenresResponse(
    val genres: List<GenreResp>
) {
    @Serializable
    data class GenreResp(
        @SerialName("id") var id : Int,
        @SerialName("name") var name : String
    )
}
