package com.example.ornamancompose.model.remote

import com.google.gson.annotations.SerializedName

data class PlantRecommendationResponse(
	val id : String,
	@SerializedName("name_alt")
	val altName : String,
	@SerializedName("image")
	val imgUrl : String,
	val desc : String
)

data class LoginResponse(
	val id : String,
	@SerializedName("access_token")
	val accessToken : String,
	val name : String,
	val email : String
)

data class RegisterResponse(
	val id : String,
	val name : String,
	val email : String
)

data class PlantDetailResponse(
	val id : String,
	val name : String,
	@SerializedName("name_alt")
	val altName: String,
	@SerializedName("image")
	val imgUrl: String,
	val desc: String,
	val light : String,
	val water : String,
	val humidity : String,
	val temperature : String,
	val food : String,
	val toxicity : String,
	val cares : String,
	val fact : String
)

data class PlantResponse(
	val id : String,
	val name : String,
	@SerializedName("name_alt")
	val altName : String,
	@SerializedName("image")
	val imgUrl : String,
	val desc : String
)

data class PlantScanResponse(
	@SerializedName("class")
	val kelas : String,
	val description : String,
	@SerializedName("image")
	val imgUrl : String
)

data class NearbySearchResponse(

	@field:SerializedName("next_page_token")
	val nextPageToken: String,

	@field:SerializedName("results")
	val results: List<ResultsItem>,

	@field:SerializedName("status")
	val status: String
)

data class Location(

	@field:SerializedName("lng")
	val lng: Double,

	@field:SerializedName("lat")
	val lat: Double
)

data class PhotosItem(

	@field:SerializedName("photo_reference")
	var photoReference: String,

)

data class ResultsItem(

	@field:SerializedName("rating")
	val rating: String,

	@field:SerializedName("photos")
	val photos: List<PhotosItem>,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("opening_hours")
	val openingHours: OpeningHours? = null,

	@field:SerializedName("geometry")
	val geometry: Geometry,

	@field:SerializedName("vicinity")
	val vicinity: String,
)

data class OpeningHours(
	@field:SerializedName("open_now")
	val openNow: Boolean = false
)

data class Geometry(

	@field:SerializedName("location")
	val location: Location
)