package com.noahutz.photobrowser.util

import retrofit2.Response
import java.lang.Exception

object ApiHelper {
    suspend fun <T> getResult(call: suspend () -> Response<T>): ResultOf<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    return ResultOf.Success(data)
                }
            }
        } catch (e: Exception) {
            return ResultOf.Failure(e.message ?: "Error getResult to function $call", e)
        }
        return ResultOf.Failure("Error getResult to function $call")
    }
}