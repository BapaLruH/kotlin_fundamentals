package ru.movie.app.ui.scenes.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.movie.app.data.model.MovieData
import ru.movie.app.data.model.MovieResult.Error
import ru.movie.app.data.model.MovieResult.Success
import ru.movie.app.ui.extensions.convertToMovie
import ru.movie.app.ui.model.Movie
import ru.movie.app.ui.repository.IMovieRepository

class ViewModelMoviesList(private val repository: IMovieRepository<MovieData>) : ViewModel() {

    private val _moviesLiveData = MutableLiveData<List<Movie>>()
    val moviesLiveData: LiveData<List<Movie>> = _moviesLiveData

    init {
        loadList()
    }

    private fun loadList() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.loadMovies()) {
                is Success -> _moviesLiveData.postValue(result.data.map { it.convertToMovie() })
                is Error -> _moviesLiveData.postValue(emptyList())
            }
        }
    }
}