package ru.movie.app.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.movie.app.R
import ru.movie.app.ui.detail.FragmentMoviesDetails
import ru.movie.app.ui.movielist.FragmentMoviesList

class ActivityMain : AppCompatActivity() {

    companion object {
        const val MOVIES_LIST_FRAGMENT_TAG = "MOVIES_LIST_FRAGMENT_TAG"
    }

    private val clickListener: (Int) -> Unit = { movieId ->
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.flContainer,
                FragmentMoviesDetails.newInstance(movieId)
            )
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupListFragment(savedInstanceState == null)
    }

    private fun setupListFragment(savedState: Boolean) {
        if (savedState) {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.flContainer,
                    FragmentMoviesList().also { it.clickListener = clickListener },
                    MOVIES_LIST_FRAGMENT_TAG
                )
                .commit()
        } else {
            ((supportFragmentManager.findFragmentByTag(MOVIES_LIST_FRAGMENT_TAG)) as? FragmentMoviesList)?.let {
                it.clickListener = clickListener
            }
        }
    }
}
