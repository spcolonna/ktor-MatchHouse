package domain.useCases.favourites

import infra.interfaces.IFavouriteRepository

class GetListFavouriteUseCase(private val repository: IFavouriteRepository) {
    fun execute(idUser: String) =
        if(repository.has(idUser))
            repository.get(idUser).idProperties
        else
            emptyList()

}