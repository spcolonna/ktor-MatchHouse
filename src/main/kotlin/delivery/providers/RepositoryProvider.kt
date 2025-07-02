package delivery.providers

import com.example.infra.repositories.*

object RepositoryProvider {

    fun getUser() = FirebaseUserRepository()

    fun getProperty() = FirebasePropertyRepository()

    fun getFavourite() = FirebaseFavouriteRepository()

    fun getDiscovery() = FirebaseDiscoveryQueueRepository()
    fun getLocation() = FirebaseLocationRepository()
    fun getFilter() = FirebaseFilterRepository()
}
