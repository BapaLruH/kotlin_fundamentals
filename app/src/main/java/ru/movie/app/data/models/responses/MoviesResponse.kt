package ru.movie.app.data.models.responses

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
    @SerialName("page") var page: Int,
    @SerialName("results") var results: List<MovieResp>
) {
    @Serializable
    data class MovieResp(
        @SerialName("id") var id: Int,                               //id
        @SerialName("title") var title: String,                      //title
        @SerialName("overview") var overview: String,                //overview
        @SerialName("poster_path") var posterPath: String?,          //poster
        @SerialName("backdrop_path") var backdropPath: String?,      //backdrop
        @SerialName("vote_average") var voteAverage: Double,         //ratings
        @SerialName("vote_count") var voteCount: Int,                //numberOfRatings
        @SerialName("adult") var adult: Boolean,                     //minimumAge
        @SerialName("genre_ids") var genreIds: List<Int>,            //genres
    )
}
