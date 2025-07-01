package com.example.delivery.request

import com.example.delivery.dtos.CreateUserDto
import com.example.delivery.dtos.UserFilterDto
import com.example.delivery.enums.UserRole


@kotlinx.serialization.Serializable
class UserFilterRequest(
    val country: String,
    val department: String,
    val neighborhood: String,
    val minPrice: Double,
    val maxPrice: Double,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Int) {
    fun toDto(userId: String) = UserFilterDto(userId, country, department, neighborhood, minPrice, maxPrice,bedrooms,bathrooms, area)

}
