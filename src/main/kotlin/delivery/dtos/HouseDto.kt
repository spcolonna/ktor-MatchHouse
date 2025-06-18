package com.example.delivery.dtos

import domain.entities.Point
import kotlinx.serialization.Serializable

@Serializable
data class House(
    var id: String,
    val point: Point,
    val title: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Double
){}