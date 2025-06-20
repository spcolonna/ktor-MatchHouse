package domain.favourites

import com.example.doubles.HouseRepositoryDouble
import delivery.dtos.AddFavouriteDto
import domain.entities.Favourites
import domain.useCases.favourites.AddFavouritesUseCase
import domain.useCases.favourites.GetListFavouriteUseCase
import doubles.FavouritesRepositoryDouble
import doubles.UserRepositoryDouble
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class AddFavouritesUseCaseTest {
    @Test
    fun `add property to favourites`(){
        val userId = "idUser"
        val houseId = "idProperty"
        val dto = AddFavouriteDto(userId, houseId)
        val repository = FavouritesRepositoryDouble().withFavourite(Favourites(userId, listOf()))
        val useCaseTestAddFavourites = AddFavouritesUseCase(repository, UserRepositoryDouble(), HouseRepositoryDouble())

        useCaseTestAddFavourites.execute(dto)

        repository.storedUserId.shouldBe(userId)
        repository.storedHouseId.shouldBe(houseId)
    }

    @Test
    fun `add another property to favourites`(){
        val userId = "anotherIdUser"
        val houseId = "anotherIdProperty"
        val dto = AddFavouriteDto(userId, houseId)
        val repository = FavouritesRepositoryDouble().withFavourite(Favourites(userId, listOf()))
        val useCaseTestAddFavourites = AddFavouritesUseCase(repository, UserRepositoryDouble(), HouseRepositoryDouble())

        useCaseTestAddFavourites.execute(dto)

        repository.storedUserId.shouldBe(userId)
        repository.storedHouseId.shouldBe(houseId)
    }


    @Test
    fun `validate user and house exist`(){
        val userId = "userId"
        val favouriteRepo = FavouritesRepositoryDouble()
        val userRepository = UserRepositoryDouble().withUserExist(true)
        val houseRepository = HouseRepositoryDouble().withHouseExist(true)
        val useCase = AddFavouritesUseCase(favouriteRepo,userRepository,houseRepository)

        val result = useCase.validate(userId, "")

        result.shouldBeTrue()
    }

    @Test
    fun `NOT validate user exist but not house`(){
        val userId = "userId"
        val favouriteRepo = FavouritesRepositoryDouble()
        val userRepository = UserRepositoryDouble().withUserExist(true)
        val houseRepository = HouseRepositoryDouble().withHouseExist(false)
        val useCase = AddFavouritesUseCase(favouriteRepo,userRepository,houseRepository)

        val result = useCase.validate(userId, "")

        result.shouldBeFalse()
    }

    @Test
    fun `NOT validate house exist but not user`(){
        val userId = "userId"
        val favouriteRepo = FavouritesRepositoryDouble()
        val userRepository = UserRepositoryDouble().withUserExist(false)
        val houseRepository = HouseRepositoryDouble().withHouseExist(false)
        val useCase = AddFavouritesUseCase(favouriteRepo,userRepository,houseRepository)

        val result = useCase.validate(userId, "")

        result.shouldBeFalse()
    }
}

