package domain.favourites

import domain.entities.Favourites
import domain.useCases.favourites.GetListFavouriteUseCase
import doubles.FavouritesRepositoryDouble
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class GetListFavouriteUseCaseTest {

    @Test
    fun `return favourites form user`(){
        val idProperty = "idProperty"
        val expected = listOf(idProperty)
        val idUser = "idUser"
        val repo = FavouritesRepositoryDouble(Favourites(idUser, listOf(idProperty)))
        val useCase = GetListFavouriteUseCase(repo)
        
        val result = useCase.execute(idUser)
        
        result.shouldBe(expected)
    }

    @Test
    fun `return another favourites form another user`(){
        val idProperty = "anotherIdProperty"
        val expected = listOf(idProperty)
        val idUser = "anotherIdUser"
        val repo = FavouritesRepositoryDouble(Favourites(idUser, listOf(idProperty)))
        val useCase = GetListFavouriteUseCase(repo)

        val result = useCase.execute(idUser)

        result.shouldBe(expected)
    }

    @Test
    fun `return empty list when user dont have a favourite`(){
        val repo = FavouritesRepositoryDouble(Favourites("idUser", listOf("idProperty")))
        val useCase = GetListFavouriteUseCase(repo)

        val result = useCase.execute("anotherIdUser")

        result.shouldBeEmpty()
    }
}

