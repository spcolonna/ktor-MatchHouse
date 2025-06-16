package com.example.delivery.response

@kotlinx.serialization.Serializable
class LoginUserResponse(var id: String, var mail: String, var token: String) {
}