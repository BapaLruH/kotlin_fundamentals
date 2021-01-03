package ru.movie.app.ui.scenes.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import coil.transform.RoundedCornersTransformation
import ru.movie.app.R
import ru.movie.app.databinding.CellActorBinding
import ru.movie.app.databinding.FragmentMoviesDetailsBinding
import ru.movie.app.ui.MainApp
import ru.movie.app.ui.extensions.dpToPxFloat
import ru.movie.app.ui.extensions.fragmentViewModels
import ru.movie.app.ui.model.Actor
import ru.movie.app.ui.model.Movie
import ru.movie.app.ui.scenes.adapter.BaseListAdapter

class FragmentMoviesDetails : Fragment() {
    private var movieId: Int? = null
    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel by fragmentViewModels {
        with(requireContext().applicationContext as MainApp) {
            ViewModelDetails(movieRepository)
        }
    }
    private lateinit var adapter: BaseListAdapter<CellActorBinding, Actor>
    private var cornerRadius: Float = 0f

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
        cornerRadius = requireContext().dpToPxFloat(6)
        initAdapter().also {
            binding.rvActors.adapter = adapter
        }
        initLiveDataObservers()
    }

    private fun updateUi(movie: Movie) {
        with(binding) {
            ivMovie.load(movie.backdrop)
            btnBack.setOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }
            tvAgeRestriction.text = getString(R.string.age_restriction, movie.minimumAge)
            tvMovieTitle.text = movie.title
            tvGenre.text = movie.genres
            tvRating.text =
                resources.getQuantityString(
                    R.plurals.reviews,
                    movie.numberOfRatings,
                    movie.numberOfRatings
                )
            customRatingBar.rating = movie.ratings
            tvMovieDescription.text = movie.overview
        }
        adapter.submitList(movie.actors)
    }

    private fun initAdapter() {
        adapter = BaseListAdapter(
            viewInflater = { layoutInflater, parent, attachToParent ->
                CellActorBinding.inflate(layoutInflater, parent, attachToParent)
            },
            bindFunction = { holder, actor ->
                with(holder) {
                    ivActorPhoto.load(actor.picture) {
                        transformations(RoundedCornersTransformation(cornerRadius))
                    }
                    tvActorName.text = actor.name
                }
            }
        )
    }

    private fun initLiveDataObservers() {
        viewModel.movieDetailLiveData.observe(viewLifecycleOwner, { movie ->
            updateUi(movie)
        })

        viewModel.movieRatingLiveData.observe(viewLifecycleOwner, { reviews ->
            binding.tvRating.text =
                this.resources.getQuantityString(R.plurals.reviews, reviews, reviews)
        })

        binding.customRatingBar.setOnRatingBarChangeListener { _, _, fromUser ->
            if (fromUser) viewModel.changeRating()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}