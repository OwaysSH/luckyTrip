package app.oways.luckytrip.hilt

import android.app.Application
import androidx.room.Room
import app.oways.luckytrip.data.local.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDatabaseModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(application: Application) =
        Room.databaseBuilder(application, AppDatabase::class.java, "lucky_trip_db")
            .fallbackToDestructiveMigration()
            .build()

    @Singleton
    @Provides
    fun providesDestinationDAO(appDatabase: AppDatabase) = appDatabase.destinationDAO()

}