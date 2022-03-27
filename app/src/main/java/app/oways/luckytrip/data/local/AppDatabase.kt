package app.oways.luckytrip.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import app.oways.luckytrip.data.local.doa.DestinationDAO
import app.oways.luckytrip.data.local.entity.DestinationEntity

@Database(
    entities = [
        DestinationEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun destinationDAO(): DestinationDAO
}