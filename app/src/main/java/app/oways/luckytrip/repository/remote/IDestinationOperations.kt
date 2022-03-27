package app.oways.luckytrip.repository.remote

import app.oways.luckytrip.data.remote.DestinationResponse
import app.oways.luckytrip.data.remote.DataState
import kotlinx.coroutines.flow.Flow

interface IDestinationOperations {

    suspend fun getDestinations(searchValue: String?): Flow<DataState<DestinationResponse?>>
}