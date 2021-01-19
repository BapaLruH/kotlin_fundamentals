package ru.movie.app.data.datasources.remote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.movie.app.data.datasources.IMovieDataSource
import ru.movie.app.data.models.Genre
import ru.movie.app.data.models.ImageSizes
import ru.movie.app.data.models.Movie
import ru.movie.app.data.models.MovieResult
import ru.movie.app.data.models.responses.PropertiesResponse
import ru.movie.app.data.services.RetrofitModule
import ru.movie.app.data.extensions.toGenre
import ru.movie.app.data.extensions.toMovie

class RemoteMovieDataSource : IMovieDataSource<Movie> {

    private val initialJob = Job()
    private lateinit var imageConfigurations: PropertiesResponse
    private val cacheStorageGenres: MutableMap<Int, Genre> by lazy { mutableMapOf() }
    private val imageProps = ImageSizes()

    init {
        CoroutineScope(initialJob + Dispatchers.IO).launch {
            loadImageProperties()
            loadGenres()
            initialJob.complete()
        }
    }

    override suspend fun loadMovies(page: Int): MovieResult<List<Movie>> {
        return try {
            val moviesResponse =
                RetrofitModule.movieApi.getListOfUpcomingMoviesInTheatres(pageNumber = page)
            initialJob.join()
            if (moviesResponse.results.isNotEmpty()) {
                MovieResult.Success(moviesResponse.results.map { movieResp ->
                    val genres = cacheStorageGenres.filter { it.key in movieResp.genreIds }.values.toList()
                    val imagePath = with(imageConfigurations.imageProperties) {
                        "$secureBaseUrl${posterSizes.find { it == imageProps.posterSize }}${movieResp.posterPath}"
                    }
                    movieResp.toMovie(imagePath, genres)
                })
            } else {
                MovieResult.Finish
            }
        } catch (e: Exception) {
            MovieResult.Error(e)
        }
    }

    override suspend fun getMovieById(id: Int): MovieResult<Movie> {
        return try {
            val movieDetailsResponse = RetrofitModule.movieApi.getPrimaryInformationAboutMovie(id)
            val castResponse = RetrofitModule.movieApi.getCastForMovie(id)

            val (backdrop, profilePosterPath) =
                with(imageConfigurations.imageProperties) {
                    "$secureBaseUrl${backdropSizes.find { it == imageProps.backdropSize }}${movieDetailsResponse.backdropPath}" to
                            "$secureBaseUrl${profileSizes.find { it == imageProps.profileSize }}"
                }

            MovieResult.Success(
                movieDetailsResponse.toMovie(profilePosterPath, backdrop, castResponse.actors)
            )
        } catch (e: Exception) {
            MovieResult.Error(e)
        }
    }

    private suspend fun loadImageProperties() {
        imageConfigurations = RetrofitModule.configurationApi.getConfiguration()
    }

    private suspend fun loadGenres() {
        val genres = RetrofitModule.genresApi.getAllGenres().genres
        if (genres.isNotEmpty()) {
            with(cacheStorageGenres) {
                clear()
                putAll(genres.associateBy({ it.id }, { it.toGenre() }))
            }
        }
    }
}