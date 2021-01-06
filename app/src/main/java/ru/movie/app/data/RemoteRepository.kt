package ru.movie.app.data

import ru.movie.app.data.model.MovieData
import ru.movie.app.data.model.MovieResult
import ru.movie.app.ui.repository.IMovieRepository

class RemoteRepository : IMovieRepository<MovieData> {
    override suspend fun loadMovies(): MovieResult<List<MovieData>> {

    }

    override suspend fun getMovieById(id: Int): MovieResult<MovieData> {

    }
}