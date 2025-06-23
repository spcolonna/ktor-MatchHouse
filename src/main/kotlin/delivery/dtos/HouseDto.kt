package com.example.delivery.dtos

import domain.entities.Point
import domain.entities.House
import kotlinx.serialization.Serializable

@Serializable
data class HouseDto(
    var id: String,
    var ownerId: String,
    val point: Point,
    val title: String,
    val price: Int,
    val bedrooms: Int,
    val bathrooms: Int,
    val area: Double,
    val imageUrls: List<String>
){
    companion object {
        fun from(property: House) =
            HouseDto(property.id,
                property.ownerId,
                property.point,
                property.title,
                property.price,
                property.bedrooms,
                property.bathrooms,
                property.area,
                property.imageUrls)
    }
}