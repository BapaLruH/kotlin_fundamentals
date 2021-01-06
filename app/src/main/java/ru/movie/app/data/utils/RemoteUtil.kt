@file:Suppress("unused")
package ru.movie.app.data.utils

import ru.movie.app.data.model.remote.ResponseResult
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

fun <T> ResponseResult<T>.isSuccess(): Boolean {
    return this is ResponseResult.Success
}

fun <T> ResponseResult<T>.asSuccess(): ResponseResult.Success<T> {
    return this as ResponseResult.Success<T>
}

@ExperimentalContracts
fun <T> ResponseResult<T>.isFailure(): Boolean {
    contract {
        returns(true) implies(this@isFailure is ResponseResult.Failure<*>)
    }
    return this is ResponseResult.Failure<*>
}

fun <T> ResponseResult<T>.asFailure(): ResponseResult.Failure<*> {
    return this as ResponseResult.Failure<*>
}