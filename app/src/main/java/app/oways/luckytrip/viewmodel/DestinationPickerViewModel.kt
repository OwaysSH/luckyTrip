package app.oways.luckytrip.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import app.oways.luckytrip.database.entity.DestinationEntity
import app.oways.luckytrip.repository.local.ILocalDestinationOperations
import app.oways.luckytrip.repository.remote.IDestinationOperations
import app.oways.luckytrip.service.DataState
import app.oways.luckytrip.service.DestinationResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationPickerViewModel @Inject constructor(
    private val repository: IDestinationOperations,
    private val localDestinationRepository: ILocalDestinationOperations
) : ViewModel() {

    private val _destinations: MutableLiveData<DataState<DestinationResponse?>> = MutableLiveData()
    val destinations: LiveData<DataState<DestinationResponse?>> get() = _destinations

    val selectedDestinationsLiveData = localDestinationRepository.getAllDestinations()

    private val _selectedDestinations: MutableLiveData<DataState<Flow<PagingData<DestinationEntity>>>> =
        MutableLiveData()
    val selectedDestinations: LiveData<DataState<Flow<PagingData<DestinationEntity>>>> get() = _selectedDestinations

    private fun onEvent(event: DestinationEvents) {
        viewModelScope.launch {
            when (event) {
                is DestinationEvents.GetRemoteDestinations -> {
                    repository.getDestinations(event.searchValue).collectLatest { dataState ->
                        when (dataState) {
                            is DataState.Success -> {
                                _destinations.postValue(dataState)
                            }
                            is DataState.GenericError, is DataState.NetworkError -> {
                                val throwable = (dataState as? DataState.GenericError)?.throwable
                                    ?: (dataState as? DataState.NetworkError)?.throwable
                            }
                        }
                    }
                }
                is DestinationEvents.InsertSelectedDestinations -> {
                    localDestinationRepository.addDestination(event.list)
                }
                is DestinationEvents.GetSelectedDestinations -> {
                    localDestinationRepository.getSelectedDestinationsPagingSource(event.orderBy)
                        .onEmpty {
                            _selectedDestinations.postValue(DataState.Empty)
                        }
                        .collectLatest {
                            _selectedDestinations.postValue(it)
                        }
                }
                is DestinationEvents.DeleteDestinationById -> {
                    localDestinationRepository.deleteDestination(event.destinationId)
                }
            }
        }
    }

    fun getRemoteDestinations(searchValue: String?) {
        onEvent(DestinationEvents.GetRemoteDestinations(searchValue))
    }

    fun addDestination(list: ArrayList<DestinationEntity>) {
        onEvent(DestinationEvents.InsertSelectedDestinations(list))
    }

    fun getLocalDestinations() {
        onEvent(DestinationEvents.GetSelectedDestinations(null))
    }

    fun deleteDestinationById(destinationId: Long) {
        onEvent(DestinationEvents.DeleteDestinationById(destinationId))
    }
}

sealed class DestinationEvents {
    class GetRemoteDestinations(val searchValue: String?= "") : DestinationEvents()
    class InsertSelectedDestinations(val list: ArrayList<DestinationEntity>) : DestinationEvents()
    class GetSelectedDestinations(val orderBy: String?) : DestinationEvents()
    class DeleteDestinationById(val destinationId: Long) : DestinationEvents()

}