package app.oways.luckytrip.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "destinations")
data class DestinationEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Long? = null,
    @ColumnInfo("city")
    val city: String? = null,
    @ColumnInfo("country_name")
    val countryName: String? = null,
    @ColumnInfo("airport_name")
    val airportName: String? = null,
    @ColumnInfo("is_video")
    val isVideo: Boolean? = false,
    @ColumnInfo("thumbnail")
    val thumbnail: String? = null
)
