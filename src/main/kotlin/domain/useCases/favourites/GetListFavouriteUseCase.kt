package domain.useCases.favourites

import infra.interfaces.IFavouriteRepository

class GetListFavouriteUseCase(private val repository: IFavouriteRepository) {
    fun execute(idUser: String) =
        if(repository.hasFavouriteList(idUser))
            repository.get(idUser).idHouses
        else
            emptyList()

}