package com.example.domain.useCases.user

import com.example.infra.interfaces.IFilterRepository
import infra.interfaces.IUserRepository

class GetFilterUseCase(private val repository: IFilterRepository, private val userRepository: IUserRepository) {

    fun validate(userId: String) = userRepository.userExists(userId)

    fun execute(userId: String) = repository.get(userId)

}
