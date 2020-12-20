package ru.movie.app.data.datasource

import ru.movie.app.data.model.Result

interface IMovieDataSource<T : Any> {
    suspend fun loadMovies(): Result<List<T>>
    suspend fun getMovieById(id: Int): Result<T>
}