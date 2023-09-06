package com.example.domain.model

data class Room(
    val id: Int,
    val name: String,
    val price: Int,
    val pricePer:  String,
    val peculiarities: List<String>,
    val imgUrls: List<String>
)