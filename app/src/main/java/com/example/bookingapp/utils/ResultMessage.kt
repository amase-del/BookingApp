package com.example.bookingapp.utils

sealed class ResultMessage<T>(val data: T? = null, val errorMessage: String? = null) {

    class Success<T>(data: T, errorMessage: String? = null) : ResultMessage<T>(data, errorMessage)
    class Error<T>(errorMessage: String, data: T? = null) : ResultMessage<T>(data, errorMessage)
    class Loading<T> : ResultMessage<T>()

}