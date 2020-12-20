package ru.movie.app.data

import ru.movie.app.data.datasource.IMovieDataSource
import ru.movie.app.data.model.MovieData
import ru.movie.app.data.model.Result
import ru.movie.app.ui.repository.IRepository

class Repository(private val dataSource: IMovieDataSource<MovieData>) : IRepository<MovieData> {
    override suspend fun loadMovies(): Result<List<MovieData>> {
        return dataSource.loadMovies()
    }

    override suspend fun getMovieById(id: Int): Result<MovieData> {
        return dataSource.getMovieById(id)
    }
}