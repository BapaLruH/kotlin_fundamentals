package ru.movie.app.data.datasources.local

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.movie.app.data.datasources.IMovieDataSource
import ru.movie.app.data.models.Actor
import ru.movie.app.data.models.Genre
import ru.movie.app.data.models.Movie
import ru.movie.app.data.models.MovieResult

class MovieDataSource(private val context: Context) : IMovieDataSource<Movie> {

    private lateinit var cashedMoviesList: Map<Int, Movie>
    private val jsonFormat = Json { ignoreUnknownKeys = true }

    override suspend fun loadMovies(page: Int): MovieResult<List<Movie>> {
        return MovieResult.Success(loadMovies(context))
    }

    override suspend fun getMovieById(id: Int): MovieResult<Movie> {
        val movie = cashedMoviesList[id] ?: return MovieResult.Error(Exception("Movie not found"))
        return MovieResult.Success(movie)
    }

    private suspend fun loadGenres(context: Context): List<Genre> =
        withContext(Dispatchers.IO) {
            val data = readAssetFileToString(context, "genres.json")
            parseGenres(data)
        }

    private fun parseGenres(data: String): List<Genre> {
        val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
        return jsonGenres.map { Genre(id = it.id, name = it.name) }
    }

    private fun readAssetFileToString(context: Context, fileName: String): String {
        val stream = context.assets.open(fileName)
        return stream.bufferedReader().readText()
    }

    private suspend fun loadActors(context: Context): List<Actor> =
        withContext(Dispatchers.IO) {
            val data = readAssetFileToString(context, "people.json")
            parseActors(data)
        }

    private fun parseActors(data: String): List<Actor> {
        val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
        return jsonActors.map { Actor(id = it.id, name = it.name, picture = it.profilePicture) }
    }

    private suspend fun loadMovies(context: Context): List<Movie> =
        withContext(Dispatchers.IO) {
            val genresMap = loadGenres(context)
            val actorsMap = loadActors(context)

            val data = readAssetFileToString(context, "data.json")
            val moviesList = parseMovies(data, genresMap, actorsMap)
            cashedMoviesList = moviesList.associateBy { it.id }
            moviesList
        }

    private fun parseMovies(
        data: String,
        genres: List<Genre>,
        actors: List<Actor>
    ): List<Movie> {
        val genresMap = genres.associateBy { it.id }
        val actorsMap = actors.associateBy { it.id }

        val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

        return jsonMovies.map { jsonMovie ->
            Movie(
                id = jsonMovie.id,
                title = jsonMovie.title,
                overview = jsonMovie.overview,
                poster = jsonMovie.posterPicture,
                backdrop = jsonMovie.backdropPicture,
                ratings = jsonMovie.ratings,
                numberOfRatings = jsonMovie.votesCount,
                minimumAge = if (jsonMovie.adult) 16 else 13,
                runtime = jsonMovie.runtime,
                genres = jsonMovie.genreIds.map {
                    genresMap[it] ?: throw IllegalArgumentException("Genre not found")
                }.joinToString(", ") { it.name },
                actors = jsonMovie.actors.map {
                    actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
                }
            )
        }
    }

    @Serializable
    private class JsonGenre(val id: Int, val name: String)

    @Serializable
    private class JsonActor(
        val id: Int,
        val name: String,
        @SerialName("profile_path")
        val profilePicture: String
    )

    @Serializable
    private class JsonMovie(
        val id: Int,
        val title: String,
        @SerialName("poster_path")
        val posterPicture: String,
        @SerialName("backdrop_path")
        val backdropPicture: String,
        val runtime: Int,
        @SerialName("genre_ids")
        val genreIds: List<Int>,
        val actors: List<Int>,
        @SerialName("vote_average")
        val ratings: Float,
        @SerialName("vote_count")
        val votesCount: Int,
        val overview: String,
        val adult: Boolean
    )
}