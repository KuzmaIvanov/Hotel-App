package com.example.data.repository

import com.example.data.mapper.BookingMapper
import com.example.data.network.service.BookingService
import com.example.domain.model.Booking
import com.example.domain.repository.BookingRepository
import javax.inject.Inject

class BookingRepositoryImpl @Inject constructor(
    private val mapper: BookingMapper,
    private val bookingService: BookingService
) : BookingRepository {

    override suspend fun getBooking(): Booking {
        return mapper.mapToDomain(bookingService.getBooking())
    }
}