package ru.movie.app.ui.repository

import ru.movie.app.data.model.Result

interface IRepository<T : Any> {
    suspend fun loadMovies(): Result<List<T>>
    suspend fun getMovieById(id: Int): Result<T>
}