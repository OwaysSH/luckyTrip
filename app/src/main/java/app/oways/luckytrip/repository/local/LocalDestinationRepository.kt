package app.oways.luckytrip.repository.local

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.oways.luckytrip.database.doa.DestinationDAO
import app.oways.luckytrip.database.entity.DestinationEntity
import app.oways.luckytrip.service.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEmpty

class LocalDestinationRepository(private val destinationDAO: DestinationDAO) :
    ILocalDestinationOperations {

    override suspend fun addDestination(list: ArrayList<DestinationEntity>) {
        destinationDAO.deleteAllDestination()
        destinationDAO.addDestination(list)
    }

    override suspend fun deleteDestination(destinationId: Long) {
        destinationDAO.deleteDestination(destinationId)
    }

    override fun getAllDestinations(): LiveData<List<DestinationEntity>?> {
        return destinationDAO.getAllDestinations()
    }

    override fun getSelectedDestinationsPagingSource(orderBy: String?): Flow<DataState<Flow<PagingData<DestinationEntity>>>> =
        flow {
            emit(DataState.Loading)
            emit(
                DataState.Success(
                    Pager(
                        config = PagingConfig(pageSize = 10),
                        pagingSourceFactory = destinationDAO.getSelectedDestinationsPagingSource().asPagingSourceFactory()
                    ).flow.onEmpty {
                        emit(DataState.Empty)
                    }
                )
            )
        }
}