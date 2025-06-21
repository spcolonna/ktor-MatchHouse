package com.example.delivery.dtos

import kotlinx.serialization.Serializable

@Serializable
class GetFavouritesDto(val userIs: String, val houses: List<HouseDto>) {
}