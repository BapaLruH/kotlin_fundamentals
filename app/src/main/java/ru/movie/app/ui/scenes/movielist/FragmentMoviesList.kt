package ru.movie.app.ui.scenes.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.google.android.material.snackbar.Snackbar
import ru.movie.app.R
import ru.movie.app.data.models.Movie
import ru.movie.app.databinding.CellMovieBinding
import ru.movie.app.databinding.FragmentMoviesListBinding
import ru.movie.app.ui.MainApp
import ru.movie.app.ui.extensions.dpToPxFloat
import ru.movie.app.ui.extensions.fragmentViewModels
import ru.movie.app.ui.models.State
import ru.movie.app.ui.scenes.adapters.BaseListAdapter


class FragmentMoviesList : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    var clickListener: ((Int) -> Unit)? = null

    private val viewModel by fragmentViewModels {
        with(requireContext().applicationContext as MainApp) {
            ViewModelMoviesList(remoteMovieRepository)
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
            binding.rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val manager = recyclerView.layoutManager
                    if (manager != null && manager is GridLayoutManager) {
                        val currentPosition = manager.findLastCompletelyVisibleItemPosition()
                        recyclerView.adapter?.let {
                            if (currentPosition >= it.itemCount - 10) viewModel.loadNextPage()
                        }
                    }
                }
            })
        }

        viewModel.moviesLiveData.observe(viewLifecycleOwner) { list ->
            list?.let {
                adapter.submitList(it)
            }
        }

        viewModel.loadingState.observe(viewLifecycleOwner) { handleLoadingState(it)}
    }

    private fun handleLoadingState(state: State?) {
        when (state) {
            is State.EmptyState -> return
            is State.LoadingState -> binding.pbLoading.isVisible = true
            is State.FinishState -> binding.pbLoading.isVisible = false
            is State.ErrorState -> {
                binding.pbLoading.isVisible = false
                Snackbar.make(requireView(), state.message, Snackbar.LENGTH_SHORT).show()
            }
        }
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
                        error(R.drawable.default_movie_poster)
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