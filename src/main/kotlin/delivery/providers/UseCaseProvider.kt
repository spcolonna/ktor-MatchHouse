package delivery.providers

import com.example.domain.useCases.AddHouseToDiscoveryQueueUseCase
import com.example.domain.useCases.favourites.DeleteFavouriteUseCase
import com.example.domain.useCases.property.GetHouseByIdUseCase
import com.example.domain.useCases.property.GetUserHousesUseCase
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
            RepositoryProvider.getCreateProperty(),
            PropertyBuilder(ServiceProvider.getIdGenerator())
        )

    fun getGetHouses() = GetHousesUseCase(RepositoryProvider.getCreateProperty())
    fun getAddFavourite() = AddFavouritesUseCase(
        RepositoryProvider.getFavourite(),
        RepositoryProvider.getUser(),
        RepositoryProvider.getCreateProperty()
    )

    fun getDeleteFavourite() = DeleteFavouriteUseCase(
        RepositoryProvider.getFavourite(),
        RepositoryProvider.getUser(),
        RepositoryProvider.getCreateProperty()
    )
    fun getFavourite() = GetListFavouriteUseCase(RepositoryProvider.getFavourite())
    fun getGetHousesById() = GetHouseByIdUseCase(RepositoryProvider.getCreateProperty())
    fun getUserHouses() = GetUserHousesUseCase(RepositoryProvider.getUser(), RepositoryProvider.getCreateProperty())

    fun getAddHouseToDiscoveryQueue() = AddHouseToDiscoveryQueueUseCase(
        RepositoryProvider.getDiscovery(),
        RepositoryProvider.getCreateProperty(),
        getCalculateDistance())
    private fun getCalculateDistance() = CalculateDistanceUseCase()
}
