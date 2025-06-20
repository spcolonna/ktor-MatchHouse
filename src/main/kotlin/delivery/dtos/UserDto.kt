package com.example.delivery.dtos

import com.example.delivery.enums.UserRole
import domain.entities.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(var id: String,
              val name: String,
              val mail: String,
              val phoneNumber: String,
              val role: UserRole,
              val agencyName: String
){
    companion object {
        fun fromUser(user: User) =
            UserDto(user.id, user.name, user.mail, user.phoneNumber, user.role, user.agencyName)
    }
}