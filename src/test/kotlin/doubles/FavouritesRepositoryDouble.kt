package doubles

import domain.entities.Favourites
import infra.interfaces.IFavouriteRepository

class FavouritesRepositoryDouble : IFavouriteRepository {

    lateinit var storedUserId: String
    lateinit var storedHouseId: String
    lateinit var removedUserId: String
    lateinit var removedHouseId: String
    private lateinit var favourites: Favourites

    override fun store(userId: String, houseId: String) {
        storedHouseId = houseId
        storedUserId = userId
    }

    override fun get(idUser: String) = favourites

    override fun hasFavouriteList(userId: String) = favourites.idUser === userId
    override fun remove(userId: String, houseId: String) {
        removedHouseId = houseId
        removedUserId = userId
    }

    fun withFavourite(favourites: Favourites) : FavouritesRepositoryDouble {
        this.favourites = favourites
        return this
    }
}