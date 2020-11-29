package ru.movie.app.ui.movielist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import ru.movie.app.R
import ru.movie.app.databinding.CellMovieBinding
import ru.movie.app.ui.model.Movie

class MoviesAdapter(
    private val movies: MutableList<Movie> = mutableListOf(),
    private val listener: ((Int) -> Unit)?
) : RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

    class MovieHolder(binding: CellMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvMovieTitle
        val pageScreen = binding.ivMovie
        val ageRestriction = binding.tvAgeRestriction
        val like = binding.ivLike
        val genre = binding.tvGenre
        val rating = binding.customRatingBar
        val reviews = binding.tvRating
        val duration = binding.tvMovieDuration
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            CellMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val context = holder.itemView.context
        val movie = movies[position]
        holder.itemView.setOnClickListener { listener?.invoke(position) }
        holder.title.text = movie.title
        holder.pageScreen.setImageDrawable(
            ResourcesCompat.getDrawable(context.resources, movie.pageScreen, context.theme)
        )
        holder.ageRestriction.text = movie.ageRestriction
        holder.like.setImageDrawable(
            ResourcesCompat.getDrawable(
                context.resources,
                if (movie.like) R.drawable.ic_like_on else R.drawable.ic_like_off, context.theme
            )
        )
        holder.genre.text = movie.genre
        holder.rating.rating = movie.rating.toFloat()
        holder.reviews.text =
            context.resources.getQuantityString(R.plurals.reviews, movie.reviews, movie.reviews)
        holder.duration.text =
            context.getString(R.string.movie_duration, movie.duration)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    fun updateList(list: List<Movie>) {
        movies.clear()
        movies.addAll(list)
        notifyDataSetChanged()
    }
}