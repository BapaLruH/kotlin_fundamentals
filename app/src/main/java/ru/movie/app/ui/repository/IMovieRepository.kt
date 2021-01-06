package ru.movie.app.ui.repository

import ru.movie.app.data.model.MovieResult

interface IMovieRepository<T : Any> {
    suspend fun loadMovies(): MovieResult<List<T>>
    suspend fun getMovieById(id: Int): MovieResult<T>
}