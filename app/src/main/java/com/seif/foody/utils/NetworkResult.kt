package com.seif.foody.utils

sealed class NetworkResult<T>(
    val data :T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?): NetworkResult<T>(data)
    class Error<T>(message: String?,data: T? = null): NetworkResult<T>(data, message) // if error happened than data = null
    class Loading<T>:NetworkResult<T>()
} // we will use this class in viewModel