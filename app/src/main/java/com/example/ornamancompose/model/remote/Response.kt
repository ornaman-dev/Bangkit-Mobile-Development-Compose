import com.google.gson.annotations.SerializedName

data class LoginResponse(
	@SerializedName("access_token")
	val accessToken : String
)

data class RegisterResponse(
	val username : String,
	val email : String,
	@SerializedName("is_active")
	val isActive : Boolean
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

data class DummyPlantResponse(
	val title : String,
	val quickDescription : String,
	val publishedAt : String,
	val publisher : String,
	val imgUrl: String = "",
	val profileImgUrl : String = ""
)

data class PlantScanResponse(
	@SerializedName("class")
	val kelas : String,
	val description : String,
	val confidence : Double,
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