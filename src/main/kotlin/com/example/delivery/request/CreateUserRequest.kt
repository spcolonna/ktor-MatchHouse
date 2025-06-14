package com.example.com.example.delivery.request

import com.example.com.example.com.example.delivery.dtos.CreateUserDto

@kotlinx.serialization.Serializable
class CreateUserRequest(val name: String, val mail: String, val password: String) {
    fun toDto() = CreateUserDto(name, mail, password)

}
