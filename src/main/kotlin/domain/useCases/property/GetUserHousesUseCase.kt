package com.example.domain.useCases.property

import com.example.infra.interfaces.IHouseRepository
import infra.interfaces.IUserRepository

class GetUserHousesUseCase(private val userRepository: IUserRepository, private val houseRepository: IHouseRepository) {

    fun validate(userId: String) = userRepository.userExists(userId)

    fun execute(userId: String) = houseRepository.getUserHouses(userId)

}
