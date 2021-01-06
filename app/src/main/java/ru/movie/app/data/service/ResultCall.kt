package ru.movie.app.data.service

import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.movie.app.data.model.ResponseResult

class ResultCall<T>(proxy: Call<T>) : CallDelegate<T, ResponseResult<T>>(proxy) {
    override fun enqueueImpl(callback: Callback<ResponseResult<T>>) {
        proxy.enqueue(ResultCallback(this, callback))
    }

    override fun cloneImpl(): Call<ResponseResult<T>> {
        return ResultCall(proxy.clone())
    }

    override fun timeout(): Timeout {
        return proxy.timeout()
    }

    private class ResultCallback<T>(
        private val proxy: ResultCall<T>,
        private val callback: Callback<ResponseResult<T>>
    ) : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            val result: ResponseResult<T> =
                if (response.isSuccessful) {
                    ResponseResult.Success.HttpResponse(
                        value = response.body() as T,
                        statusCode = response.code(),
                        statusMessage = response.message(),
                        url = call.request().url.toString()
                    )
                } else {
                    ResponseResult.Failure.HttpError(
                        HttpException(
                            statusCode = response.code(),
                            statusMessage = response.message(),
                            url = call.request().url.toString()
                        )
                    )
                }
            callback.onResponse(proxy, Response.success(result))
        }

        override fun onFailure(call: Call<T>, error: Throwable) {
            val result = when (error) {
                is retrofit2.HttpException -> ResponseResult.Failure.HttpError(
                    HttpException(error.code(), error.message(), cause = error)
                )
                else -> ResponseResult.Failure.Error(error)
            }
            callback.onResponse(proxy, Response.success(result))
        }

    }
}