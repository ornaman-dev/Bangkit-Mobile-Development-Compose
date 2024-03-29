package com.example.ornamancompose.repository


import com.example.ornamancompose.model.remote.LoginResponse
import com.example.ornamancompose.model.remote.PlantDetailResponse
import com.example.ornamancompose.model.remote.PlantResponse
import com.example.ornamancompose.model.remote.PlantScanResponse
import com.example.ornamancompose.model.remote.RegisterResponse
import com.example.ornamancompose.model.remote.ResultsItem
import android.util.Log
import com.example.ornamancompose.BuildConfig
import com.example.ornamancompose.model.datastore.AuthPreferences
import com.example.ornamancompose.model.remote.ApiService
import com.example.ornamancompose.model.remote.PlantRecommendationRequest
import com.example.ornamancompose.model.remote.PlantRecommendationResponse
import com.example.ornamancompose.model.remote.RegisterRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.HttpException
import java.io.File


class Repository(
    private val apiService: ApiService,
    private val preference : AuthPreferences
) {

    fun getUserSession() : Flow<LoginResponse> = preference.getUserSession()
    suspend fun clearSession() = preference.clearSession()

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
                preference.saveUser(
                    LoginResponse(
                        name = responseBody.name,
                        email = responseBody.email,
                        accessToken = responseBody.accessToken,
                        id = responseBody.id
                    )
                )
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

    fun register(name : String, email: String, password: String) : Flow<UiState<RegisterResponse>> = flow{
        emit(UiState.Loading)
        try{
            val body = RegisterRequestBody(
                name = name,
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

    fun getDetailPlant(id : String) : Flow<UiState<PlantDetailResponse>> = flow {
        try{
            val response = apiService.getDetailPlant(id)
            val responseBody = response.body()
            if(response.isSuccessful && responseBody != null){
                emit(UiState.Success(responseBody))
            }else{
                emit(UiState.Error(response.message(), response.code()))
            }
        }catch (e :HttpException){
            emit(UiState.Error(e.message(), e.code()))
        }catch (e : Exception){
            emit(UiState.Exception(e.message.toString()))
        }
    }

    fun recommendPlants(userId : String, plantId : String) : Flow<UiState<List<PlantRecommendationResponse>>> = flow{
        emit(UiState.Loading)
        try{
            val body = PlantRecommendationRequest(userId, plantId)
            val response = apiService.recommendPlants(body)
            val responseBody = response.body()
            if(response.isSuccessful && responseBody != null){
                emit(UiState.Success(responseBody))
            }else{
                emit(UiState.Error(response.message(), response.code()))
            }
        }catch (e :HttpException){
            emit(UiState.Error(e.message(), e.code()))
        }catch (e : Exception){
            emit(UiState.Exception(e.message.toString()))
        }
    }

}