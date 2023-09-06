package com.example.hotelapp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.module.IoDispatcher
import com.example.domain.model.Booking
import com.example.domain.repository.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _bookingUiState = MutableLiveData<Result<Booking>?>(null)
    val bookingUiState get() = _bookingUiState

    init {
        getBooking()
    }

    private fun getBooking() {
        viewModelScope.launch(ioDispatcher) {
            kotlin.runCatching {
                bookingRepository.getBooking()
            }.onFailure {
                _bookingUiState.postValue(Result.failure(it))
            }.onSuccess {
                _bookingUiState.postValue(Result.success(it))
            }
        }
    }
}