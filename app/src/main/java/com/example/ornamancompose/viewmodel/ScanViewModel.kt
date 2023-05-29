package com.example.ornamancompose.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ornamancompose.model.remote.PlantScanResponse
import com.example.ornamancompose.repository.Repository
import com.example.ornamancompose.repository.UiState
import kotlinx.coroutines.flow.Flow
import java.io.File

class ScanViewModel(private val repository: Repository) : ViewModel(){

    fun scanPlant(file : File) : Flow<UiState<PlantScanResponse>> = repository.scanPlant(file)

}