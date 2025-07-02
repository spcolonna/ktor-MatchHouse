package com.example.delivery.request

import domain.entities.Point
import kotlinx.serialization.Serializable

@Serializable
data class CreateHouseRequest(
    val ownerId: String,
    val point: Point,
    val title: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Double,
    val imageUrls: List<String>,
    val country: String,
    val department: String,
    val neighborhood: String,
)