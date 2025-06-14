package domain.useCases.user

import infra.interfaces.IUserRepository

class ModifyUserUseCase(private val userRepository: IUserRepository) {
    fun execute(userId: String, newPassword: String) {
        val newUser = userRepository.getUser(userId)
        newUser.password = newPassword
        userRepository.update(newUser)
        /*val existingUser = userRepository.userExists(user)
        return if (!existingUser) {
            false

        } else{
            val updatedUser = user.copy(password = newPassword)
            userRepository.store(updatedUser)
            true
        }*/
    }

    fun validate(userId: String) = userRepository.userExists(userId)
}