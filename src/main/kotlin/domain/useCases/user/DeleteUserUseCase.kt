package domain.useCases.user

import infra.interfaces.IUserRepository

class DeleteUserUseCase(private val userRepository: IUserRepository) {

    fun validateUser(userId: String) = userRepository.userExists(userId)

    fun executeDelete(userId: String) = userRepository.deleteUser(userId)
}