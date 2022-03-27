package app.oways.luckytrip.service.dmain

import com.google.gson.annotations.SerializedName

data class Thumbnail(
    @SerializedName("image_type")
    val image_type: String? = null,
    @SerializedName("image_url")
    val image_url: String? = null
)
