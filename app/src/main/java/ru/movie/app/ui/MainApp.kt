package ru.movie.app.ui

import android.app.Application
import ru.movie.app.data.Repository
import ru.movie.app.data.datasource.MovieDataSource

class MainApp : Application() {
    lateinit var movieRepository: Repository
        private set

    override fun onCreate() {
        super.onCreate()
        movieRepository = Repository(MovieDataSource(this))
    }
}