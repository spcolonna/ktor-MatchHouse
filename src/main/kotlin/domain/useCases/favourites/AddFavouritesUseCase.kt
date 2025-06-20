package domain.useCases.favourites

import com.example.infra.interfaces.IHouseRepository
import delivery.dtos.AddFavouriteDto
import domain.entities.Favourites
import infra.interfaces.IFavouriteRepository
import infra.interfaces.IUserRepository

class AddFavouritesUseCase(
    private val favouriteRepository: IFavouriteRepository,
    private val userRepository: IUserRepository,
    private val houseRepository: IHouseRepository) {

    fun validate(userId: String, houseId: String) =
        userRepository.userExists(userId) && houseRepository.houseExist(houseId)

    fun execute(dto: AddFavouriteDto) {
        favouriteRepository.store(dto.idUser, dto.idProperty)
    }
}