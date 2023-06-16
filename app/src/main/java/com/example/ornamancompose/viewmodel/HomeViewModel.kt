package com.example.ornamancompose.viewmodel

import com.example.ornamancompose.model.remote.PlantDetailResponse
import com.example.ornamancompose.model.remote.PlantResponse
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ornamancompose.model.remote.LoginResponse
import com.example.ornamancompose.model.remote.PlantRecommendationResponse
import com.example.ornamancompose.repository.Repository
import com.example.ornamancompose.repository.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: Repository) : ViewModel() {

    private val _allPlants = MutableStateFlow<UiState<List<PlantResponse>>>(UiState.Loading)
    val allPlants : StateFlow<UiState<List<PlantResponse>>> get() = _allPlants

    private val _detailPlant = MutableStateFlow<UiState<PlantDetailResponse>>(UiState.Loading)
    val detailPlant : StateFlow<UiState<PlantDetailResponse>> get() = _detailPlant

    private val _recommendPlants = MutableStateFlow<UiState<List<PlantRecommendationResponse>>>(UiState.Success(
        emptyList()
    ))
    val recommendPlants : StateFlow<UiState<List<PlantRecommendationResponse>>> get() = _recommendPlants

    private val _mutableUserSessionStateFlow = MutableStateFlow<UiState<LoginResponse>>(
        UiState.Success(LoginResponse("", "", "", ""))
    )
    val userSessionStateFlow : StateFlow<UiState<LoginResponse>> get() = _mutableUserSessionStateFlow

    fun getAllPlants() = viewModelScope.launch {
        repository.getAllPlants().catch {cause ->
            _allPlants.value = UiState.Exception(cause.message.toString())
        }.collect{uiState ->
            _allPlants.value = uiState
        }
    }

    fun getDetailPlant(id : String) = viewModelScope.launch {
        repository.getDetailPlant(id).catch {cause ->
            _detailPlant.value = UiState.Exception(cause.message.toString())
        }.collect{uiState ->
            _detailPlant.value = uiState
        }
    }

    fun recommendPlants(userId : String, plantId : String) = viewModelScope.launch {
        repository.recommendPlants(userId, plantId).catch {cause ->
            _recommendPlants.value = UiState.Exception(cause.message.toString())
        }.collect{uiState ->
            _recommendPlants.value = uiState
        }
    }

    fun getUserSession() = viewModelScope.launch {
        repository.getUserSession().catch {cause ->
            _mutableUserSessionStateFlow.value = UiState.Exception(cause.message.toString())
        }.collect{ uiState ->
            _mutableUserSessionStateFlow.value = UiState.Success(uiState)
        }
    }
}