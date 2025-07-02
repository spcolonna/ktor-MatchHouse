package com.example.delivery.request

import com.example.delivery.dtos.UserFilterDto


@kotlinx.serialization.Serializable
class UserFilterRequest(
    private val country: String,
    private val department: String,
    private val neighborhood: String,
    private val minPrice: Double,
    private val maxPrice: Double,
    private val bedrooms: Int,
    private val bathrooms: Int,
    private val area: Int) {
    fun toDto(userId: String) = UserFilterDto(userId, country, department, neighborhood, minPrice, maxPrice,bedrooms,bathrooms, area)

}
