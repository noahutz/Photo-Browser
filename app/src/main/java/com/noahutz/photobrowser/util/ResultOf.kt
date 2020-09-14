package com.noahutz.photobrowser.util

sealed class ResultOf<out T> {
    data class Success<T>(val data: T) : ResultOf<T>()

    data class Failure(val errorMessage: String, val throwable: Throwable? = null) :
        ResultOf<Nothing>()
}

inline fun <reified T> ResultOf<T>.doIfSuccess(function: (T) -> Unit) {
    if (this is ResultOf.Success) {
        function(data)
    }
}

inline fun <reified T> ResultOf<T>.doIfFailure(function: (String, Throwable?) -> Unit) {
    if (this is ResultOf.Failure) {
        function(errorMessage, throwable)
    }
}

inline fun <reified T, reified R> ResultOf<T>.map(transform: (T) -> R): ResultOf<R> {
    return when (this) {
        is ResultOf.Success -> ResultOf.Success(transform(data))
        is ResultOf.Failure -> this
    }
}
