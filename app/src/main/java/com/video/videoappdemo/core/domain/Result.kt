package com.video.videoappdemo.core.domain

sealed interface Result<out D, out E: Error> {
    data class Success<out D>(val data: D): Result<D, Nothing>
    data class Error<out E: com.video.videoappdemo.core.domain.Error>(val error: E): Result<Nothing, E>
}


/**
 * T - Transformation Type, E - Error Type, R - New success type
 *
 * (map: (T) -> R) - Transformation function that converts T to R
 * */
inline fun <T, E: Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when(this) {
        is Result.Error -> Result.Error(error)
        is Result.Success -> Result.Success(map(data))
    }
}