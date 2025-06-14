package infra.interfaces

import domain.entities.User

interface IUserRepository {
    fun store(user: User)
    fun getUserByMail(mail: String): User?
    fun userExists(userId: String): Boolean
    fun deleteUser(userId: String)
    fun getStoredUser(): User
    fun update(user: User)
    fun getUser(userId: String): User
}
