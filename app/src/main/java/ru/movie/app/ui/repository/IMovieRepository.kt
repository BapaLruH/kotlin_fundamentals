package ru.movie.app.ui.repository

import ru.movie.app.data.models.MovieResult

interface IMovieRepository<T : Any> {
    suspend fun loadMovies(page: Int = 1): MovieResult<List<T>>
    suspend fun getMovieById(id: Int): MovieResult<T>
}