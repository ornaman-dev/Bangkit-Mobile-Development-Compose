package com.example.ornamancompose.viewmodel

import com.example.ornamancompose.model.remote.LoginResponse
import com.example.ornamancompose.model.remote.RegisterResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ornamancompose.repository.Repository
import com.example.ornamancompose.repository.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: Repository) : ViewModel() {

    private val _mutableLoginStateFlow = MutableStateFlow<UiState<LoginResponse>>(UiState.Success(
        LoginResponse("", "", "", "")
    ))
    val loginStateFlow : StateFlow<UiState<LoginResponse>> get() = _mutableLoginStateFlow

    private val initialRegisterResponse = RegisterResponse(
        name = "",
        email = "",
        id = ""
    )

    private val _mutableRegisterStateFlow = MutableStateFlow<UiState<RegisterResponse>>(UiState.Success(initialRegisterResponse))
    val registerStateFlow : StateFlow<UiState<RegisterResponse>> get() = _mutableRegisterStateFlow

    private val _mutableUserSessionStateFlow = MutableStateFlow<UiState<LoginResponse>>(
        UiState.Success(LoginResponse("", "", "", ""))
    )
    val userSessionStateFlow : StateFlow<UiState<LoginResponse>> get() = _mutableUserSessionStateFlow


    fun login(email : String, password : String) = viewModelScope.launch {
        repository.login(email, password)
            .catch {
                _mutableLoginStateFlow.value = UiState.Exception(it.message.toString())
            }
            .collect{uiState ->
            _mutableLoginStateFlow.value = uiState
        }
    }

    fun register(name : String, email : String, password: String) = viewModelScope.launch {
        repository.register(name, email, password)
            .catch {
                _mutableRegisterStateFlow.value = UiState.Exception(it.message.toString())
            }
            .collect{uiState ->
                _mutableRegisterStateFlow.value = uiState
            }
    }

    fun getUserSession() = viewModelScope.launch {
        repository.getUserSession().catch {cause ->
            _mutableUserSessionStateFlow.value = UiState.Exception(cause.message.toString())
        }.collect{ uiState ->
            _mutableUserSessionStateFlow.value = UiState.Success(uiState)
        }
    }
    fun clearSession() = viewModelScope.launch {
        repository.clearSession()
    }

}