package com.example.ornamancompose.model.remote

import com.example.ornamancompose.BuildConfig
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.concurrent.TimeUnit


interface ApiService{
    @Multipart
    @POST("predict")
    suspend fun scanPlant(
        @Part file : MultipartBody.Part
    ) : Response<PlantScanResponse>

    @GET("${BuildConfig.GOOGLE_MAPS_BASE_API}nearbysearch/json")
    suspend fun searchNearbyPlace(
        @QueryMap request : MutableMap<String, Any>
    ) : Response<NearbySearchResponse>

    @FormUrlEncoded
    @POST("${BuildConfig.TEMP_BASE_API}login/token")
    suspend fun login(
        @FieldMap field : MutableMap<String, Any>
    ) : Response<LoginResponse>

    @POST("${BuildConfig.TEMP_BASE_API}users/")
    suspend fun register(
        @Body body : RegisterRequestBody
    ) : Response<RegisterResponse>

    @POST("${BuildConfig.PLANT_BASE_API}likes")
    suspend fun recommendPlants(
        @Body body : PlantRecommendationRequest
    ) : Response<List<PlantRecommendationResponse>>

    @GET("${BuildConfig.PLANT_BASE_API}plants")
    suspend fun getAllPlants() : Response<List<PlantResponse>>

    @GET("${BuildConfig.PLANT_BASE_API}plant")
    suspend fun getDetailPlant(
        @Query("id") id : String
    ) : Response<PlantDetailResponse>

}

class ApiConfig{
    companion object{
        private val loggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        private val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
        private val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.CLASSIFICATION_BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun getApiService() : ApiService{
            return retrofit.create(ApiService::class.java)
        }
    }
}