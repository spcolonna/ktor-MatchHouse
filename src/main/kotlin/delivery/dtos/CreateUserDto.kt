package com.example.delivery.dtos

import domain.entities.User

class CreateUserDto(private val name: String, private val mail: String, private val password: String) {
    fun toUser(id: String) = User(id, name, mail, password)

}
