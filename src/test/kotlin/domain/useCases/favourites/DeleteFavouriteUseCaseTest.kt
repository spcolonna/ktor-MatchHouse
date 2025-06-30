package com.example.domain.useCases.favourites

import com.example.doubles.HouseRepositoryDouble
import delivery.dtos.FavouriteDto
import domain.entities.Favourites
import doubles.FavouritesRepositoryDouble
import doubles.UserRepositoryDouble
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class DeleteFavouriteUseCaseTest {

    @Test
    fun `delete property to favourites`(){
        val userId = "idUser"
        val houseId = "idProperty"
        val dto = FavouriteDto(userId, houseId)
        val repository = FavouritesRepositoryDouble().withFavourite(Favourites(userId, listOf()))
        val useCase = DeleteFavouriteUseCase(repository, UserRepositoryDouble(), HouseRepositoryDouble())

        useCase.execute(dto)

        repository.removedUserId.shouldBe(userId)
        repository.removedHouseId.shouldBe(houseId)
    }

    @Test
    fun `delete another property to favourites`(){
        val userId = "anotherIdUser"
        val houseId = "anotherIdProperty"
        val dto = FavouriteDto(userId, houseId)
        val repository = FavouritesRepositoryDouble().withFavourite(Favourites(userId, listOf()))
        val useCase = DeleteFavouriteUseCase(repository, UserRepositoryDouble(), HouseRepositoryDouble())

        useCase.execute(dto)

        repository.removedUserId.shouldBe(userId)
        repository.removedHouseId.shouldBe(houseId)
    }

    @Test
    fun `validate user and house exist`(){
        val userId = "userId"
        val favouriteRepo = FavouritesRepositoryDouble()
        val userRepository = UserRepositoryDouble().withUserExist(true)
        val houseRepository = HouseRepositoryDouble().withHouseExist(true)
        val useCase = DeleteFavouriteUseCase(favouriteRepo,userRepository,houseRepository)

        val result = useCase.validate(userId, "")

        result.shouldBeTrue()
    }

    @Test
    fun `NOT validate user exist but not house`(){
        val userId = "userId"
        val favouriteRepo = FavouritesRepositoryDouble()
        val userRepository = UserRepositoryDouble().withUserExist(true)
        val houseRepository = HouseRepositoryDouble().withHouseExist(false)
        val useCase = DeleteFavouriteUseCase(favouriteRepo,userRepository,houseRepository)

        val result = useCase.validate(userId, "")

        result.shouldBeFalse()
    }

    @Test
    fun `NOT validate house exist but not user`(){
        val userId = "userId"
        val favouriteRepo = FavouritesRepositoryDouble()
        val userRepository = UserRepositoryDouble().withUserExist(false)
        val houseRepository = HouseRepositoryDouble().withHouseExist(false)
        val useCase = DeleteFavouriteUseCase(favouriteRepo,userRepository,houseRepository)

        val result = useCase.validate(userId, "")

        result.shouldBeFalse()
    }
}