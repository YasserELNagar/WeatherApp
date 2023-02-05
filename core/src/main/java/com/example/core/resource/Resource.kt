package com.example.core.resource

import androidx.annotation.Keep

@Keep
sealed class Resource<out T>(
    val status: Status,
    val data: T?,
    val error:Throwable?
) {

    class Initial<T>: Resource<T>(status = Status.COMPLETE, data = null, error = null)
    class Loading<T>: Resource<T>(status = Status.LOADING, data = null, error = null)
    class COMPLETE<T> : Resource<T>(status = Status.COMPLETE, data = null, error = null)
    class SUCCESS<T>(data: T?) : Resource<T>(status = Status.SUCCESS, data = data, error = null)
    class ERROR<T>(e:Throwable) : Resource<T>(status = Status.ERROR, data = null, error = e)

    val Resource<*>.succeeded
        get() = this is SUCCESS && data != null
}