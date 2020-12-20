package ru.movie.app.ui.movielist

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.movie.app.R
import ru.movie.app.databinding.CellMovieBinding
import ru.movie.app.ui.model.Movie

class MoviesAdapter(
    private val movies: MutableList<Movie> = mutableListOf(),
    private val listener: ((Int) -> Unit)?
) : RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

    class MovieHolder(val binding: CellMovieBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            CellMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        val context = holder.itemView.context
        val movie = movies[position]
        holder.itemView.setOnClickListener { listener?.invoke(movie.id) }
        holder.binding.tvMovieTitle.text = movie.title
        Glide.with(context)
            .load(movie.poster)
            .into(object : CustomTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    holder.binding.ivMovie.setImageDrawable(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })

        holder.binding.tvAgeRestriction.text =
            context.getString(R.string.age_restriction, movie.minimumAge)
        holder.binding.ivLike.setImageDrawable(
            ResourcesCompat.getDrawable(
                context.resources,
                if (movie.like) R.drawable.ic_like_on
                else R.drawable.ic_like_off, context.theme
            )
        )
        holder.binding.tvGenre.text = movie.genres
        holder.binding.customRatingBar.rating = movie.ratings
        holder.binding.tvRating.text =
            context.resources.getQuantityString(
                R.plurals.reviews,
                movie.numberOfRatings,
                movie.numberOfRatings
            )
        holder.binding.tvMovieDuration.text =
            context.getString(R.string.movie_duration, movie.runtime)
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