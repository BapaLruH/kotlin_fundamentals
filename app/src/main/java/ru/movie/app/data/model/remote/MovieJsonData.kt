package ru.movie.app.data.model.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieJsonData(
    @SerialName("id") var id: Int,                                                 //id
    @SerialName("title") var title: String,                                        //title
    @SerialName("overview") var overview: String,                                  //overview
    @SerialName("poster_path") var posterPath: String,                             //poster
    @SerialName("backdrop_path") var backdropPath: String,                         //backdrop
    @SerialName("vote_average") var voteAverage: Double,                           //ratings
    @SerialName("vote_count") var voteCount: Int,                                  //numberOfRatings
    @SerialName("adult") var adult: Boolean,                                       //minimumAge
    val runtime: Int = 0,
    val like: Boolean = false,
    @SerialName("genre_ids") var genreIds: List<Int>,                              //genres
)
