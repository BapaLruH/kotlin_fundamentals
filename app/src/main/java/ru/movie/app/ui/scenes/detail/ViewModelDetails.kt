package ru.movie.app.ui.scenes.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.movie.app.data.models.Movie
import ru.movie.app.data.models.MovieResult
import ru.movie.app.ui.repository.IMovieRepository

class ViewModelDetails(private val repository: IMovieRepository<Movie>) : ViewModel() {

    private val _movieDetailLiveData = MutableLiveData<Movie>()
    val movieDetailLiveData: LiveData<Movie> = _movieDetailLiveData

    private val _movieRatingLiveData = MutableLiveData(0)
    val movieRatingLiveData: LiveData<Int> = _movieRatingLiveData

    fun getMovieById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val result = repository.getMovieById(id)) {
                is MovieResult.Success -> _movieDetailLiveData.postValue(result.data)
                is MovieResult.Error -> throw  IllegalArgumentException(result.exception?.message)
            }
        }
    }

    fun changeRating() {
        _movieRatingLiveData.apply { value = this.value?.plus(1) }
    }
}