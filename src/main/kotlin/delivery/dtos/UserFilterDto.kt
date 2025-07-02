package com.example.delivery.dtos

import kotlinx.serialization.Serializable

@Serializable
class UserFilterDto(
    val userId: String,
    val country: String,
    val department: String,
    val neighborhood: String,
    val minPrice: Double,
    val maxPrice: Double,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Int
) {

}
