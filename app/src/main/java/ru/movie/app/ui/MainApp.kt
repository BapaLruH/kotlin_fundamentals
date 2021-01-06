package ru.movie.app.ui

import android.app.Application
import ru.movie.app.data.LocalRepository
import ru.movie.app.data.datasource.local.MovieDataSource

class MainApp : Application() {
    lateinit var movieRepository: LocalRepository
        private set

    override fun onCreate() {
        super.onCreate()
        movieRepository = LocalRepository(MovieDataSource(this))
    }
}