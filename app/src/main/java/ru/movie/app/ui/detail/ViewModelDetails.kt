package ru.movie.app.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.movie.app.R
import ru.movie.app.ui.model.Actor
import ru.movie.app.ui.model.MovieDetail

class ViewModelDetails : ViewModel() {
    private var moviesStore: MutableList<MovieDetail> = mutableListOf(
        MovieDetail(
            0,
            "Avengers: End Game",
            R.drawable.avengers_img,
            "13+",
            "Action, Adventure, Fantasy",
            4,
            0,
            "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
            listOf(
                Actor(R.drawable.actor_robert, "Robert Downey Jr."),
                Actor(R.drawable.actor_chris, "Chris Evans"),
                Actor(R.drawable.actor_mark, "Mark Ruffalo"),
                Actor(R.drawable.actor_chris_hemsworth, "Chris Hemsworth"),
                Actor(R.drawable.actor_chris_hemsworth, "Chris Hemsworth"),
                Actor(R.drawable.actor_chris_hemsworth, "Chris Hemsworth")
            )
        )
    )

    private val _movieDetailLiveData = MutableLiveData<MovieDetail>()
    val movieDetailLiveData: LiveData<MovieDetail> = _movieDetailLiveData

    private val _movieRatingLiveData = MutableLiveData<Int>().apply { value = 0 }
    val movieRatingLiveData: LiveData<Int> = _movieRatingLiveData

    fun getMovieById(id: Int) {
        _movieDetailLiveData.value = moviesStore[id]
    }

    fun changeRating() {
        _movieRatingLiveData.apply { value = this.value?.plus(1) }
    }
}