package com.example.ornamancompose.repository

sealed class UiState<out T: Any?> {
    object Loading : UiState<Nothing>()
    data class Success<out T: Any>(val data: T) : UiState<T>()
    data class Error(val message: String, val code : Int) : UiState<Nothing>()
    data class Exception(val message : String) : UiState<Nothing>()
}