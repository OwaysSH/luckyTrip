package app.oways.luckytrip.repository.remote

import app.oways.luckytrip.data.remote.DestinationResponse
import app.oways.luckytrip.data.remote.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class DestinationRepository constructor(private val serviceApi: DestinationServiceApi) :
    IDestinationOperations {

    override suspend fun getDestinations(searchValue: String?): Flow<DataState<DestinationResponse?>> =
        flow {
            try {
                val searchType = if (searchValue.isNullOrEmpty()) null else "city_or_country"
                val result = serviceApi.getDestinations(searchValue, searchType)
                if (result.isSuccessful) {
                    result.body()?.let {
                        emit(DataState.Success(it))
                    } ?: emit(DataState.Empty)
                } else {
                    emit(DataState.GenericError(Exception()))
                }
            } catch (throwable: Throwable) {
                emit(
                    when (throwable) {
                        is IOException -> DataState.NetworkError(throwable)
                        else -> DataState.GenericError(throwable)
                    }
                )
            }
        }
}