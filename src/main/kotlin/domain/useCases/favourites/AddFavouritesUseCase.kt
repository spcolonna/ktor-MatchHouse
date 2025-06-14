package domain.useCases.favourites

import delivery.dtos.AddFavouriteDto
import domain.entities.Favourites
import infra.interfaces.IFavouriteRepository

class AddFavouritesUseCase(private val repository: IFavouriteRepository, private val getFavouriteUseCase: GetListFavouriteUseCase) {
    fun execute(dto: AddFavouriteDto) {
        val userFavourite = getFavouriteUseCase.execute(dto.idUser)
        repository.store(Favourites(dto.idUser, userFavourite.plus(dto.idProperty)))
    }

}