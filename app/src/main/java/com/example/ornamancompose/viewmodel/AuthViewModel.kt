package com.example.ornamancompose.viewmodel

import LoginResponse
import RegisterResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ornamancompose.model.remote.RegisterRequestBody
import com.example.ornamancompose.repository.Repository
import com.example.ornamancompose.repository.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository) : ViewModel() {

    private val _mutableLoginStateFlow = MutableStateFlow<UiState<LoginResponse>>(UiState.Success(LoginResponse("")))
    val loginStateFlow : StateFlow<UiState<LoginResponse>> get() = _mutableLoginStateFlow

    private val initialRegisterResponse = RegisterResponse(
        username = "",
        email = "",
        isActive = false
    )

    private val _mutableRegisterStateFlow = MutableStateFlow<UiState<RegisterResponse>>(UiState.Success(initialRegisterResponse))
    val registerStateFlow : StateFlow<UiState<RegisterResponse>> get() = _mutableRegisterStateFlow


    fun login(email : String, password : String) = viewModelScope.launch {
        repository.login(email, password)
            .catch {
                _mutableLoginStateFlow.value = UiState.Exception(it.message.toString())
            }
            .collect{uiState ->
            _mutableLoginStateFlow.value = uiState
        }
    }

    fun register(username : String, email : String, password: String) = viewModelScope.launch {
        repository.register(username, email, password)
            .catch {
                _mutableRegisterStateFlow.value = UiState.Exception(it.message.toString())
            }
            .collect{uiState ->
                _mutableRegisterStateFlow.value = uiState
            }
    }

    fun getUserSession() = repository.getUserSession()
    fun clearSession() = viewModelScope.launch {
        repository.clearSession()
    }

}