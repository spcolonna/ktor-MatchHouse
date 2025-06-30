package com.example.delivery.request

import delivery.dtos.FavouriteDto

@kotlinx.serialization.Serializable
class AddFavoriteRequest(val userId: String, val houseId: String) {
    fun toDto() = FavouriteDto(userId,houseId)
}