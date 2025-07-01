package com.example.domain.useCases.user

import com.example.domain.entities.UserFilter
import com.example.infra.interfaces.IFilterRepository
import infra.interfaces.IUserRepository

class StoreFilterUseCase(private val repository: IFilterRepository, private val userRepository: IUserRepository) {
    fun execute(userFilter: UserFilter) = repository.store(userFilter)

    fun validate(userId: String) = userRepository.userExists(userId)

}
