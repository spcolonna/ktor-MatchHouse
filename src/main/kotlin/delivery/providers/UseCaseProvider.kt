package delivery.providers

import com.example.domain.useCases.AddHouseToDiscoveryQueueUseCase
import com.example.domain.useCases.discovery.GetDiscoveryQueueUseCase
import com.example.domain.useCases.favourites.DeleteFavouriteUseCase
import com.example.domain.useCases.houses.GetNearbyHousesUseCase
import com.example.domain.useCases.locations.GetCountriesUseCase
import com.example.domain.useCases.locations.GetDepartmentsUseCase
import com.example.domain.useCases.locations.GetNeighborhoodsUseCase
import com.example.domain.useCases.property.GetHouseByIdUseCase
import com.example.domain.useCases.property.GetUserHousesUseCase
import com.example.domain.useCases.user.GetFilterUseCase
import com.example.domain.useCases.user.StoreFilterUseCase
import com.example.infra.interfaces.IDiscoveryListRepository
import domain.builders.PropertyBuilder
import domain.useCases.CalculateDistanceUseCase
import domain.useCases.favourites.AddFavouritesUseCase
import domain.useCases.favourites.GetListFavouriteUseCase
import domain.useCases.property.CreateHouseUseCase
import domain.useCases.property.GetHousesUseCase
import domain.useCases.user.CreateUserUseCase
import domain.useCases.user.DeleteUserUseCase
import domain.useCases.user.GetUserUseCase
import domain.useCases.user.ModifyUserUseCase

object UseCaseProvider {
    fun getCreateUser() = CreateUserUseCase(RepositoryProvider.getUser())
    fun getUser() = GetUserUseCase(RepositoryProvider.getUser())
    fun getModifyUser() = ModifyUserUseCase(RepositoryProvider.getUser())
    fun getDeleteUser() = DeleteUserUseCase(RepositoryProvider.getUser())

    fun getCreateProperty() =
        CreateHouseUseCase(
            RepositoryProvider.getProperty(),
            PropertyBuilder(ServiceProvider.getIdGenerator())
        )

    fun getGetHouses() = GetHousesUseCase(RepositoryProvider.getProperty())
    fun getAddFavourite() = AddFavouritesUseCase(
        RepositoryProvider.getFavourite(),
        RepositoryProvider.getUser(),
        RepositoryProvider.getProperty()
    )

    fun getDeleteFavourite() = DeleteFavouriteUseCase(
        RepositoryProvider.getFavourite(),
        RepositoryProvider.getUser(),
        RepositoryProvider.getProperty()
    )
    fun getFavourite() = GetListFavouriteUseCase(RepositoryProvider.getFavourite())
    fun getGetHousesById() = GetHouseByIdUseCase(RepositoryProvider.getProperty())
    fun getUserHouses() = GetUserHousesUseCase(RepositoryProvider.getUser(), RepositoryProvider.getProperty())

    fun getAddHouseToDiscoveryQueue() = AddHouseToDiscoveryQueueUseCase(
        RepositoryProvider.getDiscovery(),
        RepositoryProvider.getProperty(),
        getCalculateDistance())
    private fun getCalculateDistance() = CalculateDistanceUseCase()
    fun getCountries() = GetCountriesUseCase(RepositoryProvider.getLocation())
    fun getDepartments() = GetDepartmentsUseCase(RepositoryProvider.getLocation())
    fun getNeighborhoods() = GetNeighborhoodsUseCase(RepositoryProvider.getLocation())
    fun getStoreFilter() = StoreFilterUseCase(RepositoryProvider.getFilter(), RepositoryProvider.getUser())
    fun getGetFilter() = GetFilterUseCase(RepositoryProvider.getFilter(), RepositoryProvider.getUser())
    fun getNearbyHouses() = GetNearbyHousesUseCase(RepositoryProvider.getProperty())
    fun getDiscoveryHouseQueue(): GetDiscoveryQueueUseCase {
        return GetDiscoveryQueueUseCase(RepositoryProvider.getDiscovery())
    }
}
