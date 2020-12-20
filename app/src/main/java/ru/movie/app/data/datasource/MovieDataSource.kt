package ru.movie.app.data.datasource

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.movie.app.data.model.ActorData
import ru.movie.app.data.model.GenreData
import ru.movie.app.data.model.MovieData
import ru.movie.app.data.model.Result

class MovieDataSource(private val context: Context) : IMovieDataSource<MovieData> {

    private lateinit var cashedMoviesList: Map<Int, MovieData>

    override suspend fun loadMovies(): Result<List<MovieData>> {
        return Result.Success(loadMovies(context))
    }

    override suspend fun getMovieById(id: Int): Result<MovieData> {
        val movie = cashedMoviesList[id] ?: return Result.Error(Exception("Movie not found"))
        return Result.Success(movie)
    }

    private val jsonFormat = Json { ignoreUnknownKeys = true }

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

    private suspend fun loadGenres(context: Context): List<GenreData> =
        withContext(Dispatchers.IO) {
            val data = readAssetFileToString(context, "genres.json")
            parseGenres(data)
        }

    private fun parseGenres(data: String): List<GenreData> {
        val jsonGenres = jsonFormat.decodeFromString<List<JsonGenre>>(data)
        return jsonGenres.map { GenreData(id = it.id, name = it.name) }
    }

    private fun readAssetFileToString(context: Context, fileName: String): String {
        val stream = context.assets.open(fileName)
        return stream.bufferedReader().readText()
    }

    private suspend fun loadActors(context: Context): List<ActorData> =
        withContext(Dispatchers.IO) {
            val data = readAssetFileToString(context, "people.json")
            parseActors(data)
        }

    private fun parseActors(data: String): List<ActorData> {
        val jsonActors = jsonFormat.decodeFromString<List<JsonActor>>(data)
        return jsonActors.map { ActorData(id = it.id, name = it.name, picture = it.profilePicture) }
    }

    private suspend fun loadMovies(context: Context): List<MovieData> =
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
        genres: List<GenreData>,
        actors: List<ActorData>
    ): List<MovieData> {
        val genresMap = genres.associateBy { it.id }
        val actorsMap = actors.associateBy { it.id }

        val jsonMovies = jsonFormat.decodeFromString<List<JsonMovie>>(data)

        return jsonMovies.map { jsonMovie ->
            MovieData(
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
                },
                actors = jsonMovie.actors.map {
                    actorsMap[it] ?: throw IllegalArgumentException("Actor not found")
                }
            )
        }
    }
}