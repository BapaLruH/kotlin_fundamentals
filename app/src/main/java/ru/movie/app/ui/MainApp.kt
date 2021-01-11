package ru.movie.app.ui

import android.app.Application
import ru.movie.app.data.LocalRepository
import ru.movie.app.data.RemoteRepository
import ru.movie.app.data.datasources.local.MovieDataSource
import ru.movie.app.data.datasources.remote.RemoteMovieDataSource

class MainApp : Application() {
    lateinit var movieRepository: LocalRepository
        private set
    lateinit var remoteMovieRepository: RemoteRepository
        private set

    override fun onCreate() {
        super.onCreate()
        movieRepository = LocalRepository(MovieDataSource(this))
        remoteMovieRepository = RemoteRepository(RemoteMovieDataSource())
    }
}