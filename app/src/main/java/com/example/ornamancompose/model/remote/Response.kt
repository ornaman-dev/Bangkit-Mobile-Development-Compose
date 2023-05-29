package com.example.ornamancompose.model.remote

import com.google.gson.annotations.SerializedName

data class PlantScanResponse(
    @SerializedName("class")
    val kelas : String,
    val description : String,
    val confidence : String,
    @SerializedName("image")
    val imgUrl : String
)