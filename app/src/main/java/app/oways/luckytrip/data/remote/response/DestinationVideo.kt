package app.oways.luckytrip.data.remote.response

import com.google.gson.annotations.SerializedName

data class DestinationVideo(

    @SerializedName("url")
    val url: String? = null,
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail? = null
)
