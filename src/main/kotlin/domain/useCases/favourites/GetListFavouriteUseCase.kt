package domain.useCases.favourites

import infra.interfaces.IFavouriteRepository

class GetListFavouriteUseCase(private val repository: IFavouriteRepository) {
    fun execute(idUser: String) =
        if(repository.exists(idUser, ""))
            repository.get(idUser).idHouses
        else
            emptyList()

}