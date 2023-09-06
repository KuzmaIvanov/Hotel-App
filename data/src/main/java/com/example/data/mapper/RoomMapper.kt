package com.example.data.mapper

import com.example.data.network.entity.RoomEntity
import com.example.domain.model.Room
import javax.inject.Inject

class RoomMapper @Inject constructor() {

    fun mapToDomain(roomEntity: RoomEntity): Room = with(roomEntity) {
        Room(
            id = id,
            name = name,
            price = price,
            pricePer = pricePer,
            peculiarities = peculiarities,
            imgUrls = imgUrls
        )
    }
}