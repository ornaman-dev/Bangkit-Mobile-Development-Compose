package com.example.ornamancompose.repository

import com.example.ornamancompose.model.remote.ApiService
import com.example.ornamancompose.model.remote.PlantScanResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException
import java.io.File


class Repository(private val apiService: ApiService) {

    fun scanPlant(file : File) : Flow<UiState<PlantScanResponse>> = flow {
        try{
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
            val imageMultipart : MultipartBody.Part = MultipartBody.Part.createFormData(
                "file",
                file.name,
                requestImageFile
            )
            val response = apiService.scanPlant(imageMultipart)
            val responseBody = response.body()
            if(response.isSuccessful && responseBody != null){
                emit(UiState.Success(responseBody))
            }else{
                emit(UiState.Error(response.message(), response.code()))
            }
        }catch (e : HttpException){
            emit(UiState.Error(e.message(), e.code()))
        }catch (e : Exception){
            emit(UiState.Exception(e.message.toString()))
        }
    }

}