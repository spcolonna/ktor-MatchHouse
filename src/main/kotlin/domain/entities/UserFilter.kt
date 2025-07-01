package com.example.domain.entities

import com.example.delivery.dtos.UserFilterDto

data class UserFilter(
    val userId: String,
    val country: String,
    val department: String,
    val neighborhood: String,
    val minPrice: Double,
    val maxPrice: Double,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Int) {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "country" to country,
            "department" to department,
            "neighborhood" to neighborhood,
            "minPrice" to minPrice,
            "maxPrice" to maxPrice,
            "bedrooms" to bedrooms,
            "bathrooms" to bathrooms,
            "area" to area,
        )
    }

    companion object {
        fun fromDto(dto: UserFilterDto) =
            UserFilter(dto.userId,dto.country,dto.department,dto.neighborhood,dto.minPrice,dto.maxPrice,dto.bedrooms,dto.bathrooms,dto.area)
    }

}
