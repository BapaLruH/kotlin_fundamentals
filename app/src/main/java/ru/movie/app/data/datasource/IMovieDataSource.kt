package ru.movie.app.data.datasource

import ru.movie.app.data.model.MovieResult

interface IMovieDataSource<T : Any> {
    suspend fun loadMovies(): MovieResult<List<T>>
    suspend fun getMovieById(id: Int): MovieResult<T>
}