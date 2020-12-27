package ru.movie.app.ui.extensions

import ru.movie.app.data.model.ActorData
import ru.movie.app.data.model.MovieData
import ru.movie.app.ui.model.Actor
import ru.movie.app.ui.model.Movie

fun MovieData.convertToMovie(): Movie {
    return Movie(
        this.id,
        this.title,
        this.overview,
        this.poster,
        this.backdrop,
        this.ratings / 2,
        this.numberOfRatings,
        this.minimumAge,
        this.runtime,
        this.like,
        this.genres.joinToString(", ") { it.name },
        this.actors.map { it.convertToActor() },
    )
}

fun ActorData.convertToActor(): Actor {
    return Actor(
        this.id,
        this.name,
        this.picture
    )
}