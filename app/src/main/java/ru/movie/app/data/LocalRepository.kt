package ru.movie.app.data

import ru.movie.app.data.datasource.IMovieDataSource
import ru.movie.app.data.model.MovieData
import ru.movie.app.data.model.ResponseResult
import ru.movie.app.ui.repository.IMovieRepository

class LocalRepository(private val dataSource: IMovieDataSource<MovieData>) : IMovieRepository<MovieData> {
    override suspend fun loadMovies(): ResponseResult<List<MovieData>> {
        return dataSource.loadMovies()
    }

    override suspend fun getMovieById(id: Int): ResponseResult<MovieData> {
        return dataSource.getMovieById(id)
    }
}