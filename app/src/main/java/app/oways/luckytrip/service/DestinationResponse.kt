package app.oways.luckytrip.service

import app.oways.luckytrip.service.dmain.Destination
import com.google.gson.annotations.SerializedName

data class DestinationResponse(
    @SerializedName("destinations")
    val destinationList: ArrayList<Destination>? = null
)
