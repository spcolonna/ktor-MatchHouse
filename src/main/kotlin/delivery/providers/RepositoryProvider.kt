package delivery.providers

import com.example.infra.repositories.FirebasePropertyRepository
import com.example.infra.repositories.FirebaseUserRepository

object RepositoryProvider {

    fun getUser() = FirebaseUserRepository()

    fun getCreateProperty() = FirebasePropertyRepository()
}
