package ru.movie.app.data.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.movie.app.BuildConfig
import ru.movie.app.data.model.remote.*

interface ConfigurationApi {
    @GET("configuration?api_key=${BuildConfig.API_KEY}")
    suspend fun getConfiguration(): ResponseResult<Properties>
}

interface MovieApi {
    @GET("movie/now_playing?api_key=${BuildConfig.API_KEY}")
    suspend fun getListOfMoviesInTheatres(
        @Query("language") language: String?,
        @Query("page") pageNumber: Int?,
        @Query("region") region: String?
    ): ResponseResult<List<MovieJsonData>>

    @GET("movie/upcoming?api_key=${BuildConfig.API_KEY}")
    suspend fun getListOfUpcomingMoviesInTheatres(
        @Query("language") language: String?,
        @Query("page") pageNumber: Int?,
        @Query("region") region: String?
    ): ResponseResult<List<MovieJsonData>>

    @GET("movie/popular?api_key=${BuildConfig.API_KEY}")
    suspend fun getListOfCurrentPopularMovies(
        @Query("language") language: String?,
        @Query("page") pageNumber: Int?,
        @Query("region") region: String?
    ): ResponseResult<List<MovieJsonData>>

    @GET("movie/top_rated?api_key=${BuildConfig.API_KEY}")
    suspend fun getListOfTopRatedMovies(
        @Query("language") language: String?,
        @Query("page") pageNumber: Int?,
        @Query("region") region: String?
    ): ResponseResult<List<MovieJsonData>>

    @GET("movie/{movie_id}?api_key=${BuildConfig.API_KEY}")
    suspend fun getPrimaryInformationAboutMovie(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") optional: String?
    ): ResponseResult<MovieJsonData>

    @GET("movie/{movie_id}/credits?api_key=${BuildConfig.API_KEY}")
    suspend fun getCastForMovie(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") optional: String?
    ): ResponseResult<List<ActorJsonData>>
}

interface GenresApi {
    @GET("genre/movie/list?api_key=${BuildConfig.API_KEY}")
    suspend fun getAllGenres(
        @Query("language") language: String?
    ): ResponseResult<List<GenreJsonData>>
}

interface PersonApi {
    @GET("person/{person_id}?api_key=${BuildConfig.API_KEY}")
    suspend fun getPrimaryInformationAboutPerson(
        @Path("person_id") movieId: Int,
        @Query("language") language: String?,
        @Query("append_to_response") optional: String?
    ): ResponseResult<ActorJsonData>
}

interface SearchApi {
    @GET("search/movie?api_key=${BuildConfig.API_KEY}")
    suspend fun searchForMovies(
        @Query("language") language: String?,
        @Query("query") query: String,
        @Query("page") pageNumber: Int?,
        @Query("include_adult") adult: Boolean?,
        @Query("region") region: String?,
        @Query("year") year: Int?,
        @Query("primary_release_year") primaryReleaseYear: Int?
    ): ResponseResult<List<MovieJsonData>>
}