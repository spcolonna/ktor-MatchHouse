package domain.useCases.user

import com.example.delivery.dtos.CreateUserDto
import domain.interfaces.IIdGenerator
import infra.interfaces.IUserRepository

class CreateUserUseCase(private val userRepository: IUserRepository, private val idGenerator: IIdGenerator) {
    fun execute(dto: CreateUserDto): String {
        val id = idGenerator.execute()
        userRepository.store(dto.toUser(id))
        return id
    }

    fun validate(mail: String) = userRepository.getUserByMail(mail) == null

}
