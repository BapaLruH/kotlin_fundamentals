package ru.movie.app.ui.scenes.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import ru.movie.app.R
import ru.movie.app.databinding.CellMovieBinding
import ru.movie.app.databinding.FragmentMoviesListBinding
import ru.movie.app.ui.MainApp
import ru.movie.app.ui.extensions.dpToPxFloat
import ru.movie.app.ui.extensions.fragmentViewModels
import ru.movie.app.ui.model.Movie
import ru.movie.app.ui.scenes.adapter.BaseListAdapter


class FragmentMoviesList : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    var clickListener: ((Int) -> Unit)? = null

    private val viewModel by fragmentViewModels {
        with(requireContext().applicationContext as MainApp) {
            ViewModelMoviesList(movieRepository)
        }
    }
    private lateinit var adapter: BaseListAdapter<CellMovieBinding, Movie>
    private var angleSize: Float = 0f

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        angleSize = requireContext().dpToPxFloat(6)

        initAdapter().also {
            binding.rvMovies.adapter = adapter
            binding.rvMovies.setHasFixedSize(true)
        }

        viewModel.moviesLiveData.observe(viewLifecycleOwner, { list ->
            list?.let {
                adapter.submitList(it)
            }
        })
    }

    private fun initAdapter() {
        adapter = BaseListAdapter(
            viewInflater = { layoutInflater, parent, attachToParent ->
                CellMovieBinding.inflate(layoutInflater, parent, attachToParent)
            },
            bindFunction = { holder, movie ->
                with(holder) {
                    val context = root.context
                    tvMovieTitle.text = movie.title
                    ivMovie.load(movie.poster) {
                        scale(Scale.FILL)
                        transformations(RoundedCornersTransformation(angleSize, angleSize))
                    }

                    tvAgeRestriction.text =
                        context.getString(R.string.age_restriction, movie.minimumAge)
                    ivLike.setImageDrawable(
                        ResourcesCompat.getDrawable(
                            context.resources,
                            if (movie.like) R.drawable.ic_like_on
                            else R.drawable.ic_like_off, context.theme
                        )
                    )
                    tvGenre.text = movie.genres
                    customRatingBar.rating = movie.ratings
                    tvRating.text =
                        context.resources.getQuantityString(
                            R.plurals.reviews,
                            movie.numberOfRatings,
                            movie.numberOfRatings
                        )
                    tvMovieDuration.text =
                        context.getString(R.string.movie_duration, movie.runtime)
                    root.setOnClickListener { clickListener?.invoke(movie.id) }
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}