package com.example.domain.useCases.user

import com.example.domain.entities.UserFilter
import com.example.infra.interfaces.IFilterRepository
import infra.interfaces.IUserRepository

class StoreFilterUseCase(private val filterRepository: IFilterRepository, private val userRepository: IUserRepository) {

    fun validate(userId: String) = userRepository.userExists(userId)

    fun execute(userFilter: UserFilter) =
        if(filterRepository.exist(userFilter.userId))
            filterRepository.update(userFilter)
        else
            filterRepository.store(userFilter)

}
