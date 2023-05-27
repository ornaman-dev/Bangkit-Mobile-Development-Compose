package com.example.ornamancompose.repository

import com.example.ornamancompose.model.remote.ApiService
import com.example.ornamancompose.model.remote.PlantScanResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File

class Repository(private val apiService: ApiService) {

    fun ScanPlant(image : File) : Flow<UiState<PlantScanResponse>> = flow {

    }

}