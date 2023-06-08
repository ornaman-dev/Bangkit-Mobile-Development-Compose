package com.example.ornamancompose.viewmodel

import PlantResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ornamancompose.repository.Repository
import com.example.ornamancompose.repository.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _allPlants = MutableStateFlow<UiState<List<PlantResponse>>>(UiState.Loading)
    val allPlants : StateFlow<UiState<List<PlantResponse>>> get() = _allPlants

    fun getAllPlants() = viewModelScope.launch {
        repository.getAllPlants().catch {cause ->
            _allPlants.value = UiState.Exception(cause.message.toString())
        }.collect{uiState ->
            _allPlants.value = uiState
        }
    }
}