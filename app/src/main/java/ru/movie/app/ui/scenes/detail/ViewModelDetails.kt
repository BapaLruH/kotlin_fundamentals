package ru.movie.app.ui.scenes.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.movie.app.data.model.MovieData
import ru.movie.app.data.model.Result
import ru.movie.app.ui.extensions.convertToMovie
import ru.movie.app.ui.model.Movie
import ru.movie.app.ui.repository.IRepository

class ViewModelDetails(private val repository: IRepository<MovieData>) : ViewModel() {

    private val _movieDetailLiveData = MutableLiveData<Movie>()
    val movieDetailLiveData: LiveData<Movie> = _movieDetailLiveData

    private val _movieRatingLiveData = MutableLiveData(0)
    val movieRatingLiveData: LiveData<Int> = _movieRatingLiveData

    fun getMovieById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getMovieById(id)) {
                is Result.Success -> _movieDetailLiveData.postValue(result.data.convertToMovie())
                is Result.Error -> throw  IllegalArgumentException(result.exception.message)
            }
        }
    }

    fun changeRating() {
        _movieRatingLiveData.apply { value = this.value?.plus(1) }
    }
}