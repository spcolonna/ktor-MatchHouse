package com.example.delivery.request

import delivery.dtos.AddFavouriteDto

@kotlinx.serialization.Serializable
class AddFavoriteRequest(val userId: String, val houseId: String) {
    fun toDto() = AddFavouriteDto(userId,houseId)
}