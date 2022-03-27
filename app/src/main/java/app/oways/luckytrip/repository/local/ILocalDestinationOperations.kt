package app.oways.luckytrip.repository.local

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import app.oways.luckytrip.data.local.entity.DestinationEntity
import app.oways.luckytrip.data.remote.DataState
import kotlinx.coroutines.flow.Flow

interface ILocalDestinationOperations {

    suspend fun addDestination(list: ArrayList<DestinationEntity>)

    suspend fun deleteDestination(destinationId: Long)

    fun getAllDestinations(): LiveData<List<DestinationEntity>?>

    fun getSelectedDestinationsPagingSource(orderBy: String?): Flow<DataState<Flow<PagingData<DestinationEntity>>>>
}