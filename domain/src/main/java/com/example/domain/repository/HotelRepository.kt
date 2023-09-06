package com.example.domain.repository

import com.example.domain.model.Hotel

interface HotelRepository {
    suspend fun getHotel(): Hotel
}