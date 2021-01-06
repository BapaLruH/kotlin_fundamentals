package ru.movie.app.data.datasource.remote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ru.movie.app.data.datasource.IMovieDataSource
import ru.movie.app.data.model.GenreData
import ru.movie.app.data.model.MovieData
import ru.movie.app.data.model.MovieResult
import ru.movie.app.data.service.RetrofitModule
import ru.movie.app.data.utils.asSuccess
import ru.movie.app.data.utils.isSuccess
import ru.movie.app.data.utils.toGenreData

class RemoteMovieDataSource : IMovieDataSource<MovieData> {

    private lateinit var genresTempStorage: MutableMap<Int, GenreData>

    init {
        CoroutineScope(Job() + Dispatchers.IO).launch {
            val result = RetrofitModule.genresApi.getAllGenres(null)
            if (result.isSuccess()) {
                genresTempStorage = mutableMapOf()
                result.asSuccess().value.forEach {
                    genresTempStorage[it.id] = it.toGenreData()
                }
            }
        }
    }

    override suspend fun loadMovies(): MovieResult<List<MovieData>> {

    }

    override suspend fun getMovieById(id: Int): MovieResult<MovieData> {

    }
}