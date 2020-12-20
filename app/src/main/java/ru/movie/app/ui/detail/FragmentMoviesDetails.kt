package ru.movie.app.ui.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.movie.app.R
import ru.movie.app.databinding.FragmentMoviesDetailsBinding
import ru.movie.app.ui.AppViewModelFactory
import ru.movie.app.ui.model.Movie

class FragmentMoviesDetails : Fragment() {
    private var movieId: Int? = null
    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ViewModelDetails
    private lateinit var adapter: ActorsAdapter

    companion object {
        const val ARG_MOVIE_ID = "movieId"

        @JvmStatic
        fun newInstance(movieId: Int) =
            FragmentMoviesDetails().apply {
                arguments = Bundle().apply {
                    putInt(ARG_MOVIE_ID, movieId)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            movieId = it.getInt(ARG_MOVIE_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        movieId?.let { viewModel.getMovieById(it) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ActorsAdapter()
        binding.rvActors.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
        binding.rvActors.adapter = adapter


        viewModel = ViewModelProvider(this, AppViewModelFactory).get(ViewModelDetails::class.java)
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

    private fun updateUi(movie: Movie) {
        Glide.with(this)
            .load(movie.backdrop)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    binding.ivMovie.setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }

            })

        binding.btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        binding.tvAgeRestriction.text = getString(R.string.age_restriction, movie.minimumAge)
        binding.tvMovieTitle.text = movie.title
        binding.tvGenre.text = movie.genres
        binding.tvRating.text =
            resources.getQuantityString(
                R.plurals.reviews,
                movie.numberOfRatings,
                movie.numberOfRatings
            )
        binding.customRatingBar.rating = movie.ratings
        binding.tvMovieDescription.text = movie.overview
        adapter.update(movie.actors)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}