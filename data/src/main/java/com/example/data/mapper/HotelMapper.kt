package com.example.data.mapper

import com.example.data.network.entity.HotelResponse
import com.example.domain.model.Hotel
import javax.inject.Inject

class HotelMapper @Inject constructor() {

    fun mapToDomain(hotelResponse: HotelResponse): Hotel = with(hotelResponse) {
        Hotel(
            id = id,
            name = name,
            address = address,
            minimalPrice = minimalPrice,
            priceForIt = priceForIt,
            rating = rating,
            ratingName = ratingName,
            imgUrls = imgUrls,
            description = aboutTheHotel.description,
            peculiarities = aboutTheHotel.peculiarities
        )
    }
}