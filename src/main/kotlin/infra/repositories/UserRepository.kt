package com.example.infra.repositories

import domain.entities.User
import infra.interfaces.IUserRepository

class UserRepository : IUserRepository {
    override fun store(user: User) {

    }

    override fun getUserByMail(mail: String): User? {
        return null
    }

    override fun userExists(userId: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun deleteUser(userId: String) {
        TODO("Not yet implemented")
    }

    override fun getStoredUser(): User {
        TODO("Not yet implemented")
    }

    override fun update(user: User) {
        TODO("Not yet implemented")
    }

    override fun getUser(userId: String): User {
        TODO("Not yet implemented")
    }
}