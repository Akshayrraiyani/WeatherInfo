package com.nooro.data.remote.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.nooro.data.remote.model.ErrorResponseDTO
import com.nooro.data.remote.result.NetworkResult
import com.nooro.data.remote.result.handleException
import retrofit2.Response

internal fun <T> Response<T>.handleBaseResponse(): NetworkResult<T> {
    return try {
        if (this.isSuccessful) {
            body()?.let {
                NetworkResult.Success(it)
            } ?: NetworkResult.Error("No data available")
        } else {
            if(errorBody()!=null) {
                val gson = Gson()
                val type = object : TypeToken<ErrorResponseDTO>() {}.type
                val errorResponse: ErrorResponseDTO? = gson.fromJson(errorBody()!!.charStream(), type)
                NetworkResult.Error("Error: ${code()} ${message()}", errorResponse = errorResponse)
            }else{
                NetworkResult.Error("Error: ${code()} ${message()}")
            }
        }
    } catch (e: Exception) {
        handleException(e)
    }
}
