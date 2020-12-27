package ru.movie.app.ui

import android.app.Application
import ru.movie.app.data.Repository
import ru.movie.app.data.datasource.MovieDataSource

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val movieRepository = Repository(MovieDataSource(this))
        AppViewModelFactory.inject(movieRepository)
    }
}