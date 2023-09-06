package com.example.data.network.entity

import com.google.gson.annotations.SerializedName

data class RoomEntity(
    val id: Int,
    val name: String,
    val price: Int,
    @SerializedName("price_per") val pricePer:  String,
    val peculiarities: List<String>,
    @SerializedName("image_urls") val imgUrls: List<String>
)