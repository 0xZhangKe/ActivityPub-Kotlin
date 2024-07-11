package com.zhangke.activitypub.utils

import com.google.gson.Gson
import com.zhangke.activitypub.exception.ActivityPubHttpException
import com.zhangke.activitypub.exception.handleErrorResponseToException
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by ZhangKe on 2022/12/3.
 */
internal class ResultCallAdapterFactory(
    private val gson: Gson,
    private val onAuthorizeFailed: () -> Unit,
) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        // Kotlin suspend function will wrap result with retrofit.Call
        if (Call::class.java != getRawType(returnType)) return null
        if (returnType !is ParameterizedType) {
            return null
        }
        val responseType = getParameterUpperBound(0, returnType)
        if (responseType !is ParameterizedType) return null
        val responseRawType = getRawType(responseType)
        if (!Result::class.java.isAssignableFrom(responseRawType)) {
            return null
        }
        return ResultCallAdapter(gson, getParameterUpperBound(0, responseType), onAuthorizeFailed)
    }
}

private class ResultCallAdapter(
    private val gson: Gson,
    private val type: Type,
    private val onAuthorizeFailed: () -> Unit,
) : CallAdapter<Any, Call<Result<*>>> {

    override fun responseType(): Type = type

    @Suppress("UNCHECKED_CAST")
    override fun adapt(call: Call<Any>): Call<Result<*>> {
        return ResultCall(call, gson, onAuthorizeFailed) as Call<Result<*>>
    }
}

private class ResultCall<S>(
    private val delegate: Call<S>,
    private val gson: Gson,
    private val onAuthorizeFailed: () -> Unit,
) : Call<Result<S>> {

    override fun enqueue(callback: Callback<Result<S>>) {
        return delegate.enqueue(object : Callback<S> {

            override fun onResponse(call: Call<S>, response: Response<S>) {
                if (response.isSuccessful && response.body() != null) {
                    val result = Result.success(response.body()!!)
                    callback.onResponse(this@ResultCall, Response.success(response.code(), result))
                } else {
                    val errorResponseException = handleErrorResponseToException(gson, response)
                    if (errorResponseException is ActivityPubHttpException.UnauthorizedException) {
                        onAuthorizeFailed()
                    }
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(Result.failure(errorResponseException))
                    )
                }
            }

            override fun onFailure(p0: Call<S>, p1: Throwable) {
                callback.onResponse(this@ResultCall, Response.success(Result.failure(p1)))
            }
        })
    }

    override fun isExecuted(): Boolean = delegate.isExecuted

    override fun clone(): Call<Result<S>> {
        return ResultCall(delegate.clone(), gson, onAuthorizeFailed)
    }

    override fun cancel() {
        delegate.cancel()
    }

    override fun isCanceled(): Boolean = delegate.isCanceled

    override fun execute(): Response<Result<S>> {
        throw UnsupportedOperationException("ResponseCall not support execute")
    }

    override fun request(): Request = delegate.request()

    override fun timeout(): Timeout = delegate.timeout()
}