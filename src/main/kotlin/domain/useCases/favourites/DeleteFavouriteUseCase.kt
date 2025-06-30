package com.example.domain.useCases.favourites

import com.example.infra.interfaces.IHouseRepository
import delivery.dtos.FavouriteDto
import infra.interfaces.IFavouriteRepository
import infra.interfaces.IUserRepository

class DeleteFavouriteUseCase(
    private val favouriteRepository: IFavouriteRepository,
    private val userRepository: IUserRepository,
    private val houseRepository: IHouseRepository
) {
    fun execute(dto: FavouriteDto) = favouriteRepository.remove(dto.idUser, dto.idProperty)
    fun validate(userId: String, houseId: String) =
        userRepository.userExists(userId) && houseRepository.houseExist(houseId)

}
