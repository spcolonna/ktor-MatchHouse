package delivery.providers

import com.example.infra.repositories.FirebaseDiscoveryQueueRepository
import com.example.infra.repositories.FirebaseFavouriteRepository
import com.example.infra.repositories.FirebasePropertyRepository
import com.example.infra.repositories.FirebaseUserRepository

object RepositoryProvider {

    fun getUser() = FirebaseUserRepository()

    fun getCreateProperty() = FirebasePropertyRepository()

    fun getFavourite() = FirebaseFavouriteRepository()

    fun getDiscovery() = FirebaseDiscoveryQueueRepository()
}
