package com.example.data.module

import com.example.data.repository.BookingRepositoryImpl
import com.example.data.repository.HotelRepositoryImpl
import com.example.data.repository.RoomRepositoryImpl
import com.example.domain.repository.BookingRepository
import com.example.domain.repository.HotelRepository
import com.example.domain.repository.RoomRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindHotelRepository(
        hotelRepositoryImpl: HotelRepositoryImpl
    ): HotelRepository

    @Singleton
    @Binds
    fun bindRoomRepository(
        roomRepositoryImpl: RoomRepositoryImpl
    ): RoomRepository

    @Singleton
    @Binds
    fun bindBookingRepository(
        bookingRepositoryImpl: BookingRepositoryImpl
    ): BookingRepository
}