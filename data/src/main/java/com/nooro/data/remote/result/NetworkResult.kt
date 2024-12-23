package com.nooro.data.remote.result

import com.nooro.data.remote.model.ErrorResponseDTO

sealed class NetworkResult<out T> {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val message: String, val cause: Throwable? = null, val errorResponse: ErrorResponseDTO? = null) : NetworkResult<Nothing>()
}
