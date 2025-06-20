package domain.useCases.user
import com.example.delivery.dtos.UserDto
import com.example.delivery.enums.UserRole
import domain.entities.User
import infra.interfaces.IUserRepository

class GetUserUseCase(private val repository: IUserRepository) {
    fun execute(id: String) =
        if(repository.userExists(id))
            UserDto.fromUser(repository.getUser(id))
        else
            UserDto.fromUser(User(id,"","","",UserRole.PERSON,""))
}
