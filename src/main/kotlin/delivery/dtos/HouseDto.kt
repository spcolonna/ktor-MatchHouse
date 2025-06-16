package com.example.delivery.dtos

import kotlinx.serialization.Serializable

@Serializable
data class House(
    val id: String,
    val title: String,
    val address: String,
    val price: Double,
    val imageUrl: String,
    val distanceKm: Double
){}