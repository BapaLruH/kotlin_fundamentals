package ru.movie.app.ui

import android.app.Application
import ru.movie.app.data.LocalRepository
import ru.movie.app.data.RemoteRepository
import ru.movie.app.data.datasources.local.MovieDataSource
import ru.movie.app.data.datasources.remote.RemoteMovieDataSource

class MainApp : Application() {
    val movieRepository: LocalRepository by lazy { LocalRepository(MovieDataSource(this)) }
    val remoteMovieRepository: RemoteRepository by lazy { RemoteRepository(RemoteMovieDataSource()) }
}