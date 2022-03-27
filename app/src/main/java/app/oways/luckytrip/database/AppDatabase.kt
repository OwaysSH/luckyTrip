package app.oways.luckytrip.database

import androidx.room.Database
import androidx.room.RoomDatabase
import app.oways.luckytrip.database.doa.DestinationDAO
import app.oways.luckytrip.database.entity.DestinationEntity

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