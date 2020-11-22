package ru.movie.app.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.movie.app.R
import ru.movie.app.databinding.ActivityMovieDetailsBinding
import ru.movie.app.ui.model.MovieDetail

class ActivityMovieDetails : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var viewModel: ViewModelDetails
    private lateinit var adapter: ActorsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ActorsAdapter()
        binding.rvActors.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.rvActors.adapter = adapter


        viewModel = ViewModelProvider(this).get(ViewModelDetails::class.java)
        viewModel.movieDetailLiveData.observe(this, { movie ->
            updateUi(movie)
        })

        viewModel.movieRatingLiveData.observe(this, { reviews ->
            binding.tvRating.text =
                this.resources.getQuantityString(R.plurals.reviews, reviews, reviews)
        })

        binding.customRatingBar.setOnRatingBarChangeListener { _, _, fromUser ->
            if (fromUser) viewModel.changeRating()
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getMovieById(0)
    }

    private fun updateUi(movie: MovieDetail) {
        binding.ivMovie.setImageDrawable(
            ResourcesCompat.getDrawable(
                resources,
                movie.pageScreen,
                theme
            )
        )
        binding.tvAgeRestriction.text = movie.ageRestriction
        binding.tvMovieTitle.text = movie.title
        binding.tvGenre.text = movie.genre
        binding.tvRating.text =
            resources.getQuantityString(R.plurals.reviews, movie.reviews, movie.reviews)
        binding.customRatingBar.rating = movie.rating.toFloat()
        binding.tvMovieDescription.text = movie.description
        adapter.update(movie.actors)
    }
}