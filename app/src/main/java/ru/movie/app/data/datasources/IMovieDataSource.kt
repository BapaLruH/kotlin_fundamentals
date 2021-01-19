package ru.movie.app.data.datasources

import ru.movie.app.data.models.MovieResult

interface IMovieDataSource<T : Any> {
    suspend fun loadMovies(page: Int): MovieResult<List<T>>
    suspend fun getMovieById(id: Int): MovieResult<T>
}