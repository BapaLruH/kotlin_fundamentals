package ru.movie.app.data.model

sealed class MovieResult<out T> {
    data class Success<out T : Any>(val data: T) : MovieResult<T>()
    data class Error(val exception: Exception) : MovieResult<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data = $data]"
            is Error -> "Error[exception = $exception]"
        }
    }
}
