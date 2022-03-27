package app.oways.luckytrip.data.remote

import app.oways.luckytrip.data.remote.response.Destination
import com.google.gson.annotations.SerializedName

data class DestinationResponse(
    @SerializedName("destinations")
    val destinationList: ArrayList<Destination>? = null
)
