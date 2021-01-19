package ru.movie.app.ui.scenes.movielist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.movie.app.data.models.Movie
import ru.movie.app.data.models.MovieResult.*
import ru.movie.app.ui.models.State
import ru.movie.app.ui.repository.IMovieRepository

class ViewModelMoviesList(private val repository: IMovieRepository<Movie>) : ViewModel() {

    private var currentPage = 1
    private var isLoaded = false

    private val _loadingState = MutableLiveData<State>(State.EmptyState)
    val loadingState: LiveData<State> = _loadingState

    private val _moviesLiveData = MutableLiveData<List<Movie>>()
    val moviesLiveData: LiveData<List<Movie>> = _moviesLiveData

    init {
        loadList()
    }

    fun loadNextPage() {
        viewModelScope.launch(Dispatchers.IO) {
            if (loadingState.value is State.LoadingState || isLoaded) return@launch
            _loadingState.postValue(State.LoadingState)
            currentPage++
            when (val result = repository.loadMovies(currentPage)) {
                is Success -> {
                    _moviesLiveData.postValue(
                        _moviesLiveData.value?.plus(result.data)

                    )
                    _loadingState.postValue(State.FinishState("Loading complete"))
                }
                is Finish -> {
                    isLoaded = true
                    _loadingState.postValue(State.FinishState("Loading complete"))
                }
                is Error -> _loadingState.postValue(
                    State.ErrorState(
                        result.exception?.message ?: "oops. something went wrong!"
                    )
                )
            }
        }
    }
    
    private fun loadList() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.loadMovies()) {
                is Success -> _moviesLiveData.postValue(result.data)
                is Error -> _moviesLiveData.postValue(emptyList())
            }
        }
    }
}