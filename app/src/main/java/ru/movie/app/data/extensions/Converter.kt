package ru.movie.app.data.extensions

import ru.movie.app.data.models.Actor
import ru.movie.app.data.models.Genre
import ru.movie.app.data.models.Movie
import ru.movie.app.data.models.responses.ActorsResponse.Cast
import ru.movie.app.data.models.responses.GenresResponse.GenreResp
import ru.movie.app.data.models.responses.MovieDetailsResponse
import ru.movie.app.data.models.responses.MoviesResponse.MovieResp

fun MovieResp.toMovie(posterPath: String, genres: List<Genre>): Movie {
    return Movie(
        id,
        title,
        overview,
        posterPath,
        backdropPath?: "",
        voteAverage.toFloat(),
        voteCount,
        if (adult) 16 else 13,
        0,
        false,
        genres.joinToString(", ") { it.name }
    )
}

fun MovieDetailsResponse.toMovie(profilePosterPart: String, backdrop: String, actors: List<Cast>): Movie {
    return Movie(
        id,
        title,
        overview,
        posterPath,
        backdrop,
        voteAverage.toFloat(),
        voteCount,
        if (adult) 16 else 13,
        runtime,
        false,
        genres.joinToString(", ") { it.name },
        actors.map { it.toActor(profilePosterPart) }.toList()
    )
}

fun GenreResp.toGenre(): Genre {
    return Genre(id, name)
}

fun Cast.toActor(profilePosterPart: String): Actor {
    return Actor(id, name, if (picture != null) "$profilePosterPart$picture" else "")
}