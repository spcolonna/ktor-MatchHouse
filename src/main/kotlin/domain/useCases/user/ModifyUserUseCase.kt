package domain.useCases.user

import com.example.delivery.dtos.CreateUserDto
import infra.interfaces.IUserRepository

class ModifyUserUseCase(private val userRepository: IUserRepository) {
    fun execute(dto: CreateUserDto) {
        val userToUpdate = dto.toUser()
        return userRepository.update(userToUpdate)
    }

    fun validate(userId: String) = userRepository.userExists(userId)
}