package com.gabo.moviesapp.other.responseHelpers

sealed class ResponseHandler<T : Any> {
    data class Success<T : Any>(val data: T?) : ResponseHandler<T>()
    data class Error<T : Any>(val errorMSg: String?) : ResponseHandler<T>()
}