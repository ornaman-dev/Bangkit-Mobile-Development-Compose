package com.example.ornamancompose.model.remote

import NearbySearchResponse
import PlantScanResponse
import com.example.ornamancompose.BuildConfig
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
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
}

class ApiConfig{
    companion object{
        private val client = OkHttpClient.Builder()
            .readTimeout(40, TimeUnit.SECONDS)
            .connectTimeout(40, TimeUnit.SECONDS)
            .build()
        private val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        fun getApiService() : ApiService{
            return retrofit.create(ApiService::class.java)
        }
    }
}