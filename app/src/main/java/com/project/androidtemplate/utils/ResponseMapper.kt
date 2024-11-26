package com.project.androidtemplate.utils

sealed class ResponseMapper<T> {

    data class Success<T>(val data: T) : ResponseMapper<T>()

    data class Error<T>(val throwable: Throwable): ResponseMapper<T>()

}