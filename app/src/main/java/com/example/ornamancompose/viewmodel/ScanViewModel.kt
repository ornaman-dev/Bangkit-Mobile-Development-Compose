package com.example.ornamancompose.viewmodel

import PlantScanResponse
import ResultsItem
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ornamancompose.repository.Repository
import com.example.ornamancompose.repository.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.io.File

class ScanViewModel(private val repository: Repository) : ViewModel(){

    private val _scanPlantState = MutableStateFlow<UiState<PlantScanResponse>>(UiState.Loading)
    val scanPlantState : StateFlow<UiState<PlantScanResponse>> get() = _scanPlantState

    private val _searchNearbyStoreState = MutableStateFlow<UiState<List<ResultsItem>>>(UiState.Loading)
    val searchNearbyStoreState : StateFlow<UiState<List<ResultsItem>>> get() = _searchNearbyStoreState

    fun scanPlant(file : File) = viewModelScope.launch {
        repository.scanPlant(file).catch { cause ->
            _scanPlantState.value = UiState.Exception(cause.message.toString())
        }.collect {uiState ->
            _scanPlantState.value = uiState
        }
    }

    fun searchNearbyStore(lat : String, long : String) = viewModelScope.launch {
        repository.searchNearbyStore(lat, long).catch { cause ->
            _searchNearbyStoreState.value = UiState.Exception(cause.message.toString())
            Log.e("Nearby-ViewModel", "Error", cause)
        }.collect { uiState ->
            _searchNearbyStoreState.value = uiState
        }
    }

    // Medan coordinate
    //"3.5955262740367937%2C98.66983043841861"
}