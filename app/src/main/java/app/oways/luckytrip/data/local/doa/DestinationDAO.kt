package app.oways.luckytrip.data.local.doa

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.oways.luckytrip.data.local.entity.DestinationEntity

@Dao
interface DestinationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDestination(destinationEntity: DestinationEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addDestination(spamCalls: List<DestinationEntity>)

    @Query("Delete from destinations where id =:destinationId")
    suspend fun deleteDestination(destinationId: Long)

    @Query("DELETE FROM destinations")
    suspend fun deleteAllDestination()

    @Query("SELECT * FROM destinations")
    fun getAllDestinations(): LiveData<List<DestinationEntity>?>

    @Query("SELECT * FROM destinations ORDER BY id ASC")
    fun getSelectedDestinationsPagingSource(): DataSource.Factory<Int, DestinationEntity>

}