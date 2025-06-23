package infra.interfaces

import domain.entities.Favourites

interface IFavouriteRepository {
    fun store(userId: String, houseId: String)
    fun get(idUser: String): Favourites
    fun hasFavouriteList(userId: String): Boolean
    fun remove(userId: String, houseId: String)
}