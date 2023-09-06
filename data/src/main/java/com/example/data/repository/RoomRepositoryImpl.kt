package com.example.data.repository

import com.example.data.mapper.RoomMapper
import com.example.data.network.service.RoomsService
import com.example.domain.model.Room
import com.example.domain.repository.RoomRepository
import javax.inject.Inject

class RoomRepositoryImpl @Inject constructor(
    private val roomMapper: RoomMapper,
    private val roomsService: RoomsService
) : RoomRepository {

    override suspend fun getRooms(): List<Room> {
        return roomsService.getRooms().rooms.map { roomMapper.mapToDomain(it) }
    }
}