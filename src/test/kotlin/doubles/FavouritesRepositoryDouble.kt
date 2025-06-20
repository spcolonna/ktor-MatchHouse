package doubles

import domain.entities.Favourites
import infra.interfaces.IFavouriteRepository

class FavouritesRepositoryDouble : IFavouriteRepository {

    lateinit var storedUserId: String
    lateinit var storedHouseId: String
    private lateinit var favourites: Favourites

    override fun store(userId: String, houseId: String) {
        storedHouseId = houseId
        storedUserId = userId
    }

    override fun get(idUser: String) = favourites

    override fun exists(userId: String, houseId: String) = favourites.idUser === userId

    fun withFavourite(favourites: Favourites) : FavouritesRepositoryDouble {
        this.favourites = favourites
        return this
    }
}