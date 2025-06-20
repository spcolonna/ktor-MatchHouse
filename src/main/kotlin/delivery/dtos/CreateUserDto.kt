package com.example.delivery.dtos

import com.example.delivery.enums.UserRole
import domain.entities.User

class CreateUserDto(
    private val id: String,
    private val name: String,
    private val mail: String,
    private val phoneNumber: String,
    private val role: UserRole,
    private val agencyName: String
) {
    fun toUser() = User(id, name, mail, phoneNumber, role, agencyName)

}
