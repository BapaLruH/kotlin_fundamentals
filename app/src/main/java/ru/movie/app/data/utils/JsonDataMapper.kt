package ru.movie.app.data.utils

import ru.movie.app.data.model.GenreData
import ru.movie.app.data.model.remote.GenreJsonData

fun GenreJsonData.toGenreData(): GenreData = GenreData(id, name)