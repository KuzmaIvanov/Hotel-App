package com.example.domain.repository

import com.example.domain.model.Booking

interface BookingRepository {

    suspend fun getBooking(): Booking
}