package com.example.ornamancompose.viewmodel

import LoginResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _mutableRegisterStateFlow = MutableStateFlow<UiState<Boolean>>(UiState.Success(false))
    val registerStateFlow : StateFlow<UiState<Boolean>> get() = _mutableRegisterStateFlow

    fun login(email : String, password : String) = viewModelScope.launch {
        repository.login(email, password)
            .catch {
                _mutableLoginStateFlow.value = UiState.Exception(it.message.toString())
            }
            .collect{uiState ->
            _mutableLoginStateFlow.value = uiState
        }
    }

    fun register(name : String, username: String, password: String) = viewModelScope.launch {
        repository.dummyRegister(name, username, password)
            .catch {
                _mutableRegisterStateFlow.value = UiState.Exception(it.message.toString())
            }
            .collect{uiState ->
                _mutableRegisterStateFlow.value = uiState
            }
    }

}