package ru.movie.app.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.movie.app.R
import ru.movie.app.ui.model.Movie

class ViewModelMoviesList : ViewModel() {
    private val moviesStore = mutableListOf<Movie>().apply {
        for (i in 0..5) {
            this.add(
                Movie(
                    0,
                    "Avengers: End Game",
                    R.drawable.movie,
                    "13+",
                    "Action, Adventure, Fantasy",
                    4,
                    0,
                    true,
                    137
                )
            )
        }
    }

    private val _moviesLiveData = MutableLiveData<List<Movie>>()
    val moviesLiveData: LiveData<List<Movie>> = _moviesLiveData

    fun loadList() {
        _moviesLiveData.value = moviesStore
    }
}