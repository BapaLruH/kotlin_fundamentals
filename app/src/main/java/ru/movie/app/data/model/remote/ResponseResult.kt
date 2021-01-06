@file:Suppress("unused")

package ru.movie.app.data.model.remote

import ru.movie.app.data.service.HttpException
import ru.movie.app.data.service.HttpResponse

sealed class ResponseResult<out T> {
    sealed class Success<T> : ResponseResult<T>() {
        abstract val value: T
        override fun toString() = "Success($value)"
        class Value<T>(override val value: T) : Success<T>()
        data class HttpResponse<T>(
            override val value: T,
            override val statusCode: Int,
            override val statusMessage: String?,
            override val url: String?

        ) : Success<T>(), ru.movie.app.data.service.HttpResponse

        object Empty : Success<Nothing>() {
            override val value: Nothing get() = error("No value")
            override fun toString(): String = "Success"
        }
    }

    sealed class Failure<E : Throwable>(open val error: E? = null) : ResponseResult<Nothing>() {
        override fun toString() = "Failure($error)"
        class Error(override val error: Throwable) : Failure<Throwable>(error)
        class HttpError(override val error: HttpException) : Failure<HttpException>(),
            HttpResponse {
            override val statusCode: Int get() = error.statusCode
            override val statusMessage: String? get() = error.statusMessage
            override val url: String? get() = error.url
        }
    }
}

typealias EmptyResult = ResponseResult<Nothing>
