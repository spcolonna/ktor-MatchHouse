package delivery.providers

import domain.builders.PropertyBuilder
import domain.useCases.favourites.AddFavouritesUseCase
import domain.useCases.favourites.GetListFavouriteUseCase
import domain.useCases.property.CreatePropertyUseCase
import domain.useCases.property.GetHousesUseCase
import domain.useCases.user.CreateUserUseCase
import domain.useCases.user.DeleteUserUseCase
import domain.useCases.user.GetUserUseCase
import domain.useCases.user.ModifyUserUseCase
import infra.interfaces.IFavouriteRepository

object UseCaseProvider {
    fun getCreateUser() = CreateUserUseCase(RepositoryProvider.getUser())
    fun getUser() = GetUserUseCase(RepositoryProvider.getUser())
    fun getModifyUser() = ModifyUserUseCase(RepositoryProvider.getUser())
    fun getDeleteUser() = DeleteUserUseCase(RepositoryProvider.getUser())

    fun getCreateProperty() =
        CreatePropertyUseCase(
            RepositoryProvider.getCreateProperty(),
            PropertyBuilder(ServiceProvider.getIdGenerator())
        )

    fun getGetHouses() = GetHousesUseCase(RepositoryProvider.getCreateProperty())
    fun getAddFavourite(): AddFavouritesUseCase {
        return AddFavouritesUseCase(
            RepositoryProvider.getFavourite(),
            RepositoryProvider.getUser(),
            RepositoryProvider.getCreateProperty()
        )
    }

    private fun getFavouriteList() = GetListFavouriteUseCase(RepositoryProvider.getFavourite())

}
