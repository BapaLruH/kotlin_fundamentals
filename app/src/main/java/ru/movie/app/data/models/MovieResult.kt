package ru.movie.app.data.models

sealed class MovieResult<out T> {
    data class Success<out T : Any>(val data: T) : MovieResult<T>()
    object Finish: MovieResult<Nothing>()
    data class Error(val exception: Throwable?) : MovieResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data = $data]"
            is Finish -> "Finish"
            is Error -> "Error[exception = $exception]"
        }
    }
}
