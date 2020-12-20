package ru.movie.app.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.movie.app.data.Repository
import ru.movie.app.ui.detail.ViewModelDetails
import ru.movie.app.ui.movielist.ViewModelMoviesList
import ru.movie.app.ui.repository.IRepository

object AppViewModelFactory : ViewModelProvider.Factory {
    private lateinit var repository: Repository
    fun inject(movieRepository: Repository) {
        repository = movieRepository
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        when {
            ViewModelMoviesList::class.java.isAssignableFrom(modelClass) -> {
                return modelClass.getConstructor(IRepository::class.java).newInstance(
                    repository
                )
            }
            ViewModelDetails::class.java.isAssignableFrom(modelClass) -> {
                return modelClass.getConstructor(IRepository::class.java).newInstance(
                    repository
                )
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}