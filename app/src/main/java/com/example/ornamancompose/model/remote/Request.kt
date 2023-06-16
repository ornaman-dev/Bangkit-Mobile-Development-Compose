package com.example.ornamancompose.model.remote

import com.google.gson.annotations.SerializedName

data class RegisterRequestBody(
    val name : String,
    val email : String,
    val password : String
)

data class PlantRecommendationRequest(
    @SerializedName("user_id")
    val userId : String,
    @SerializedName("plant_id")
    val plantId : String
)