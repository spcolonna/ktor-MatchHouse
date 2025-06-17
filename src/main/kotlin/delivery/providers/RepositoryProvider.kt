package delivery.providers

import com.example.infra.repositories.FirebasePropertyRepository
import com.example.infra.repositories.UserRepository
import infra.interfaces.ICreatePropertyRepository
import infra.interfaces.IUserRepository

object RepositoryProvider {
    fun getUser(): IUserRepository {
        return UserRepository()
    }

    fun getCreateProperty() = FirebasePropertyRepository()
}
