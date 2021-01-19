package ru.movie.app.data

import ru.movie.app.data.datasources.IMovieDataSource
import ru.movie.app.data.models.Movie
import ru.movie.app.data.models.MovieResult
import ru.movie.app.ui.repository.IMovieRepository

class RemoteRepository(private val movieDataSource: IMovieDataSource<Movie>) : IMovieRepository<Movie>  {
    override suspend fun loadMovies(page: Int): MovieResult<List<Movie>> {
        return movieDataSource.loadMovies(page)
    }

    override suspend fun getMovieById(id: Int): MovieResult<Movie> {
        return movieDataSource.getMovieById(id)
    }
}