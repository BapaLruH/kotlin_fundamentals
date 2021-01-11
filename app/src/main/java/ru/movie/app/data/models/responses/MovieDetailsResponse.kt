package ru.movie.app.data.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailsResponse(
    @SerialName("id") var id : Int,
    @SerialName("title") var title : String,
    @SerialName("overview") var overview : String,
    @SerialName("poster_path") var posterPath : String,
    @SerialName("backdrop_path") var backdropPath : String,
    @SerialName("vote_average") var voteAverage : Double,
    @SerialName("vote_count") var voteCount : Int,
    @SerialName("runtime") var runtime : Int,
    @SerialName("adult") var adult : Boolean,
    @SerialName("genres") var genres : List<GenresResponse.GenreResp>
)
