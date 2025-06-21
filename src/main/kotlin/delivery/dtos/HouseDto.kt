package com.example.delivery.dtos

import domain.entities.Point
import domain.entities.Property
import kotlinx.serialization.Serializable

@Serializable
data class HouseDto(
    var id: String,
    val point: Point,
    val title: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Double
){
    companion object {
        fun from(property: Property) =
            HouseDto(property.id,property.point, property.title, property.price, property.bedrooms, property.bathrooms, property.area)
    }
}