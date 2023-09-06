package com.example.data.mapper

import com.example.data.network.entity.BookingEntity
import com.example.domain.model.Booking
import javax.inject.Inject

class BookingMapper @Inject constructor() {

    fun mapToDomain(bookingEntity: BookingEntity): Booking = with(bookingEntity) {
        Booking(
            id = id,
            hotelName = hotelName,
            hotelAddress = hotelAddress,
            hotelRating = hotelRating,
            ratingName = ratingName,
            departure = departure,
            arrivalCountry = arrivalCountry,
            tourDateStart = tourDateStart,
            tourDateStop = tourDateStop,
            numberOfNights = numberOfNights,
            room = room,
            nutrition = nutrition,
            tourPrice = tourPrice,
            fuelCharge = fuelCharge,
            serviceCharge = serviceCharge
        )
    }
}