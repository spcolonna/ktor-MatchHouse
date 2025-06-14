package doubles

import domain.entities.Favourites
import infra.interfaces.IFavouriteRepository

class FavouritesRepositoryDouble(private val favourites: Favourites) : IFavouriteRepository {

    lateinit var storedFavourites: Favourites
    override fun store(favourites: Favourites) {
        storedFavourites = favourites
    }

    override fun get(idUser: String) = favourites

    override fun has(idUser: String) = favourites.idUser === idUser
}