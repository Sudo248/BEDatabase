package com.nhom2.bedatabase.common

// states of data when call API or Database
sealed class Result<out T>{
    // When success return a data that load from API or Database
    data class Success<out T>(val data: T) : Result<T>()
    // When error return a message
    data class Error(val message: String) : Result<Nothing>()
    // When loading
    object Loading : Result<Nothing>()
}
