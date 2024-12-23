package com.nooro.data.remote.model

data class ErrorResponseDTO(
    var error: Error = Error(),
) {
    data class Error(
        var code: Int = 0,
        var message: String? = null,
    )
}