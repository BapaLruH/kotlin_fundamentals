package ru.movie.app.data.services

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.movie.app.data.models.responses.*

interface ConfigurationApi {
    @GET("configuration")
    suspend fun getConfiguration(): PropertiesResponse
}

interface MovieApi {
    @GET("movie/upcoming")
    suspend fun getListOfUpcomingMoviesInTheatres(
        @Query("language") language: String? = "en-US",
        @Query("page") pageNumber: Int? = 1
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getPrimaryInformationAboutMovie(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = "en-US"
    ): MovieDetailsResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getCastForMovie(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String? = "en-US"
    ): ActorsResponse
}

interface GenresApi {
    @GET("genre/movie/list")
    suspend fun getAllGenres(
        @Query("language") language: String? = "en-US"
    ): GenresResponse
}