package ru.movie.app.ui.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.movie.app.R
import ru.movie.app.ui.model.Movie

class ViewModelMoviesList : ViewModel() {
    private val moviesStore = mutableListOf<Movie>().apply {
        this.add(
            Movie(0, "Avengers: End Game", R.drawable.movie_avengers, "13+", "Action, Adventure, Fantasy", 4, 0, false, 137)
        )
        this.add(
            Movie(1, "Tenet", R.drawable.movie_tenet, "16+", "Action, Sci-Fi, Thriller", 5, 0, true, 98)
        )
        this.add(
            Movie(2, "Black Widow", R.drawable.movie_black_widow, "13+", "Action, Adventure, Sci-Fi", 4, 0, false, 38)
        )
        this.add(
            Movie(3, "Wonder Woman 1984", R.drawable.movie_wonder_woman, "13+", "Action, Adventure, Fantasy", 5, 0, false, 74)
        )
    }

    private val _moviesLiveData = MutableLiveData<List<Movie>>()
    val moviesLiveData: LiveData<List<Movie>> = _moviesLiveData

    fun loadList() {
        _moviesLiveData.value = moviesStore
    }
}