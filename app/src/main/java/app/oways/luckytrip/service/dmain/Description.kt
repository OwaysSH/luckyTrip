package app.oways.luckytrip.service.dmain

import com.google.gson.annotations.SerializedName

data class Description(
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("object_id")
    val object_id: Long? = null,
    @SerializedName("object_type")
    val object_type: String? = null,
    @SerializedName("description_type")
    val description_type: String? = null,
    @SerializedName("text")
    val text: String? = null,
    @SerializedName("language_code")
    val language_code: String? = null,
    @SerializedName("translated")
    val translated: Int? = null
)
