package com.example.data.network.entity

import com.google.gson.annotations.SerializedName

data class HotelResponse(
    val id: Int,
    val name: String,
    @SerializedName("adress") val address: String,
    @SerializedName("minimal_price") val minimalPrice: Int,
    @SerializedName("price_for_it") val priceForIt: String,
    val rating: Byte,
    @SerializedName("rating_name") val ratingName: String,
    @SerializedName("image_urls") val imgUrls: List<String>,
    @SerializedName("about_the_hotel") val aboutTheHotel: AboutTheHotel
)