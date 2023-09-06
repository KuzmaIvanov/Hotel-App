package com.example.data.network.service

import com.example.data.network.entity.RoomsResponse
import retrofit2.http.GET

interface RoomsService {

    @GET("f9a38183-6f95-43aa-853a-9c83cbb05ecd")
    suspend fun getRooms(): RoomsResponse
}