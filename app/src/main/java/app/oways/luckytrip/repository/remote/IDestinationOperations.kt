package app.oways.luckytrip.repository.remote

import app.oways.luckytrip.service.DestinationResponse
import app.oways.luckytrip.service.DataState
import kotlinx.coroutines.flow.Flow

interface IDestinationOperations {

    suspend fun getDestinations(searchValue: String?): Flow<DataState<DestinationResponse?>>
}