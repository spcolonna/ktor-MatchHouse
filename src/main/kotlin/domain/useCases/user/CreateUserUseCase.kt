package domain.useCases.user

import com.example.delivery.dtos.CreateUserDto
import infra.interfaces.IUserRepository

class CreateUserUseCase(private val userRepository: IUserRepository) {

    fun validate(mail: String) = userRepository.getUserByMail(mail) == null

    fun execute(dto: CreateUserDto) = userRepository.store(dto.toUser())

}
