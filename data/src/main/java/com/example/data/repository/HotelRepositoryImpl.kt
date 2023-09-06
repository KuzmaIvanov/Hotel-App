package com.example.data.repository

import com.example.data.mapper.HotelMapper
import com.example.data.network.service.HotelService
import com.example.domain.model.Hotel
import com.example.domain.repository.HotelRepository
import javax.inject.Inject

class HotelRepositoryImpl @Inject constructor(
    private val hotelService: HotelService,
    private val hotelMapper: HotelMapper
) : HotelRepository {

    override suspend fun getHotel(): Hotel {
        return hotelMapper.mapToDomain(hotelService.getHotel())
    }
}