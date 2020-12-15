package ru.movie.app.ui.movielist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import ru.movie.app.databinding.FragmentMoviesListBinding


class FragmentMoviesList : Fragment() {

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!
    var clickListener: ((Int) -> Unit)? = null

    private lateinit var viewModel: ViewModelMoviesList
    private lateinit var adapter: MoviesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MoviesAdapter(listener = clickListener)
        binding.rvMovies.layoutManager =
            GridLayoutManager(requireContext(), 2)
        binding.rvMovies.adapter = adapter

        viewModel = ViewModelProvider(this).get(ViewModelMoviesList::class.java)
        viewModel.moviesLiveData.observe(this, { list ->
            list?.let {
                adapter.updateList(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}