package delivery.providers

import domain.builders.PropertyBuilder
import domain.useCases.property.CreatePropertyUseCase
import domain.useCases.property.GetHousesUseCase
import domain.useCases.user.CreateUserUseCase
import domain.useCases.user.DeleteUserUseCase

object UseCaseProvider {
    fun getCreateUser() = CreateUserUseCase(RepositoryProvider.getUser())
    fun getDeleteUser() = DeleteUserUseCase(RepositoryProvider.getUser())

    fun getCreateProperty() =
        CreatePropertyUseCase(
            RepositoryProvider.getCreateProperty(),
            PropertyBuilder(ServiceProvider.getIdGenerator())
        )

    fun getGetHouses() = GetHousesUseCase(RepositoryProvider.getCreateProperty())

}
