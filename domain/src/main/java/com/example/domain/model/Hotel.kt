package com.example.domain.model

data class Hotel(
    val id: Int,
    val name: String,
    val address: String,
    val minimalPrice: Int,
    val priceForIt: String,
    val rating: Byte,
    val ratingName: String,
    val imgUrls: List<String>,
    val description: String,
    val peculiarities: List<String>
)