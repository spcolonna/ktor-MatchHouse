package delivery.providers

import com.example.infra.repositories.UserRepository
import infra.interfaces.IUserRepository

object RepositoryProvider {
    fun getUser(): IUserRepository {
        return UserRepository()
    }

}
