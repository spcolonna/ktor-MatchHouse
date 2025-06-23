package com.example.delivery.request

import com.example.delivery.dtos.CreateUserDto


@kotlinx.serialization.Serializable
class UserPositionRequest(val lat: Double?, val lon: Double?) {

}
