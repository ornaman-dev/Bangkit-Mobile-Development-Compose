package com.example.ornamancompose.repository


import LoginResponse
import PlantResponse
import PlantScanResponse
import RegisterResponse
import ResultsItem
import android.util.Log
import com.example.ornamancompose.BuildConfig
import com.example.ornamancompose.model.remote.ApiService
import com.example.ornamancompose.model.remote.RegisterRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
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
                val confidence = responseBody.confidence * 100.0
                val data = responseBody.copy(
                    confidence = confidence
                )
                emit(UiState.Success(data))
            }else{
                emit(UiState.Error(response.message(), response.code()))
            }
        }catch (e : HttpException){
            emit(UiState.Error(e.message(), e.code()))
        }catch (e : Exception){
            emit(UiState.Exception(e.message.toString()))
        }
    }

    // Override this with the actual implementation

    fun login(email: String, password: String) : Flow<UiState<LoginResponse>> = flow {
        Log.i("Repo-TAG", "$email \t $password")
        emit(UiState.Loading)
        try{
            //Todo(field username should be email instead, contact back-end for further improve)
            val field = mutableMapOf<String,Any>(
                "username" to email,
                "password" to password
            )
            val response = apiService.login(field)
            val responseBody = response.body()
            if(response.isSuccessful && responseBody != null){
                emit(UiState.Success(responseBody))
            }else{
                val errorResponse = response.errorBody()?.string()?.let { JSONObject(it) }
                val message = errorResponse?.getString("detail")
                emit(UiState.Error(message!!, response.code()))
            }
        }catch (e : HttpException){
            emit(UiState.Error(e.message(), e.code()))
        }catch (e : Exception){
            emit(UiState.Exception(e.message.toString()))
        }
    }

    fun dummyLogin(username : String, password : String) : Flow<UiState<Boolean>> = flow {
        emit(UiState.Loading)
        val loginStatus = username == "jokohartono12" && password == "jokohartono12"
        if(loginStatus){
            emit(UiState.Success(loginStatus))
        }else{
            emit(UiState.Error("Unnable to login", 400))
        }
    }

    fun register(username : String, email: String, password: String) : Flow<UiState<RegisterResponse>> = flow{
        emit(UiState.Loading)
        try{
            val body = RegisterRequestBody(
                username = username,
                email = email,
                password = password
            )
            val response = apiService.register(body)
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

    fun dummyRegister(name : String, username: String, password: String) : Flow<UiState<Boolean>> = flow{
        emit(UiState.Loading)
        val loginStatus = username.length >= 8 && password.length >= 8
        if(loginStatus){
            emit(UiState.Success(loginStatus))
        }else{
            emit(UiState.Error("Unnable to register", 400))
        }
    }

    //Todo(the nearby search place is succeeded but provide an empty list of result, test all of the query below in postman to debug it)
    fun searchNearbyStore(lat : String, long : String) : Flow<UiState<List<ResultsItem>>> = flow {
        val request = mutableMapOf<String, Any>()
        request["keyword"] = "toko pupuk|toko perlengkapan tanaman|toko perlengkapan kebun"
        request["location"] = "$lat,$long"
        request["rankby"] = "distance"
        request["key"] = BuildConfig.GOOGLE_MAPS_API_KEY
        try{
            val response = apiService.searchNearbyPlace(request)
            val responseBody = response.body()
            if(response.isSuccessful && responseBody != null){
//                for(result in responseBody.results){
//                    for(photo in result.photos){
//                        photo.photoReference = setPhotoReference(photo.photoReference)
//                    }
//                }
                emit(UiState.Success(responseBody.results))
                Log.e("NEARBY-TAG", "${response.raw()}")
                Log.e("NEARBY-TAG", "${response.errorBody()}")
                Log.e("NEARBY-TAG", "${response.body()}")
            }else{
                emit(UiState.Error(response.message(), response.code()))
            }
        }catch (e : Exception){
            emit(UiState.Exception(e.message.toString()))
            Log.e("NEARBY-TAG", "Error", e)
        }
    }

    fun getAllPlants() : Flow<UiState<List<PlantResponse>>> = flow{
        try {
            val response = apiService.getAllPlants()
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