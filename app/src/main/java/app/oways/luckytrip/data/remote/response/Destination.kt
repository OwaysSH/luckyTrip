package app.oways.luckytrip.data.remote.response

import com.google.gson.annotations.SerializedName

data class Destination(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("city")
    val city: String? = null,
    @SerializedName("country_name")
    val countryName: String? = null,
    @SerializedName("airport_name")
    val airportName: String? = null,
    @SerializedName("country_code")
    val countryCode: String? = null,
    @SerializedName("latitude")
    val latitude: Double? = null,
    @SerializedName("longitude")
    val longitude: Double? = null,
    @SerializedName("iata_code")
    val iataCode: String? = null,
    @SerializedName("iata_parent_id")
    val iataParentId: Long? = null,
    @SerializedName("is_enabled")
    val isEnabled: String? = null,
    @SerializedName("temperature")
    val temperature: Int? = null,
    @SerializedName("original_destination_id")
    val originalDestinationId: Long? = null,
    @SerializedName("destination_video")
    val destinationVideo: DestinationVideo? = null,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail? = null,
    @SerializedName("description")
    val description: Description? = null,
    @SerializedName("destination_images")
    val destinationImages: ArrayList<String>? = null
)