package com.example.delivery.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateHouseRequest(
    val ownerId: String,
    val lat: Double,
    val lon: Double,
    val title: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Double
)
