package com.example.hotelapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.module.IoDispatcher
import com.example.domain.model.Room
import com.example.domain.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _roomsUiState = MutableLiveData<Result<List<Room>>?>(null)
    val roomsUiState get() = _roomsUiState

    init {
        getRooms()
    }

    private fun getRooms() {
        viewModelScope.launch(ioDispatcher) {
            kotlin.runCatching {
                roomRepository.getRooms()
            }.onFailure {
                _roomsUiState.postValue(Result.failure(it))
            }.onSuccess {
                _roomsUiState.postValue(Result.success(it))
            }
        }
    }
}