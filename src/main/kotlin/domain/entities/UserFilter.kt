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

    fun toDto() =
        UserFilterDto(userId,country, department, neighborhood, minPrice, maxPrice, bedrooms, bathrooms, area)

    companion object {
        fun fromDto(dto: UserFilterDto) =
            UserFilter(dto.userId,dto.country,dto.department,dto.neighborhood,dto.minPrice,dto.maxPrice,dto.bedrooms,dto.bathrooms,dto.area)

        fun fromMap(userId: String, map: Map<String, Any>): UserFilter {
            return UserFilter(
                userId = userId,
                country = map["country"] as? String ?: "Uruguay",
                department = map["department"] as? String ?: "",
                neighborhood = map["neighborhood"] as? String ?: "",
                minPrice = (map["minPrice"] as? Number)?.toDouble() ?: 0.0,
                maxPrice = (map["maxPrice"] as? Number)?.toDouble() ?: 500000.0,
                bedrooms = (map["bedrooms"] as? Number)?.toInt() ?: 0,
                bathrooms = (map["bathrooms"] as? Number)?.toInt() ?: 0,
                area = (map["area"] as? Number)?.toInt() ?: 0
            )
        }
    }

}
