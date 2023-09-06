package com.example.domain.repository

import com.example.domain.model.Room

interface RoomRepository {
    suspend fun getRooms(): List<Room>
}