package domain.favourites

import delivery.dtos.AddFavouriteDto
import domain.entities.Favourites
import domain.useCases.favourites.AddFavouritesUseCase
import domain.useCases.favourites.GetListFavouriteUseCase
import doubles.FavouritesRepositoryDouble
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class AddFavouritesUseCaseTest {
    @Test
    fun `add property to favourites`(){
       val expected = Favourites("idUser", listOf("idProperty"))
        val dto = AddFavouriteDto("idUser", "idProperty")
        val repository = FavouritesRepositoryDouble(Favourites("idUser", listOf()))
        val getFavouriteUseCase = GetListFavouriteUseCase(repository)
        val useCaseTestAddFavourites = AddFavouritesUseCase(repository, getFavouriteUseCase)

        useCaseTestAddFavourites.execute(dto)
        repository.storedFavourites.shouldBe(expected)
    }

    @Test
    fun `add another property to favourites`(){
        val expected = Favourites("anotherIdUser", listOf("anotherIdProperty"))
        val dto = AddFavouriteDto("anotherIdUser", "anotherIdProperty")
        val repository = FavouritesRepositoryDouble(Favourites("anotherIdUser", listOf()))
        val getFavouriteUseCase = GetListFavouriteUseCase(repository)
        val useCaseTestAddFavourites = AddFavouritesUseCase(repository, getFavouriteUseCase)
        useCaseTestAddFavourites.execute(dto)
        repository.storedFavourites.shouldBe(expected)
    }

    @Test
    fun `add more than one property to favourites`(){
        val expected = Favourites("idUser", listOf("idProperty", "anotherIdProperty"))
        val dto = AddFavouriteDto("idUser", "anotherIdProperty")
        val repository = FavouritesRepositoryDouble(Favourites("idUser", listOf("idProperty")))
        val getFavouriteUseCase = GetListFavouriteUseCase(repository)
        val useCaseTestAddFavourites = AddFavouritesUseCase(repository, getFavouriteUseCase)

        useCaseTestAddFavourites.execute(dto)

        repository.storedFavourites.shouldBe(expected)
    }

}

