package infra.interfaces

import domain.entities.Favourites

interface IFavouriteRepository {
    fun store(favourites: Favourites)
    fun get(idUser: String): Favourites
    fun has(idUser: String): Boolean
}