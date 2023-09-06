package com.example.hotelapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.module.IoDispatcher
import com.example.domain.model.Hotel
import com.example.domain.repository.HotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelViewModel @Inject constructor(
    private val hotelRepository: HotelRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _hotelUiState = MutableLiveData<Result<Hotel>?>(null)
    val hotelUiState get() = _hotelUiState

    init {
        getHotel()
    }

    private fun getHotel() {
        viewModelScope.launch(ioDispatcher) {
            kotlin.runCatching {
                hotelRepository.getHotel()
            }.onFailure {
                _hotelUiState.postValue(Result.failure(it))
            }.onSuccess {
                _hotelUiState.postValue(Result.success(it))
            }
        }
    }
}