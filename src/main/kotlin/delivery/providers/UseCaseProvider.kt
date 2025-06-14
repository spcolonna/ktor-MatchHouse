package delivery.providers

import domain.useCases.user.CreateUserUseCase
import domain.useCases.user.DeleteUserUseCase

object UseCaseProvider {
    fun getCreateUser() = CreateUserUseCase(RepositoryProvider.getUser(),ServiceProvider.getIdGenerator())

    fun getDeleteUser() = DeleteUserUseCase(RepositoryProvider.getUser())

}
