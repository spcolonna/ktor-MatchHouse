package com.example.domain.useCases.houses

import com.example.domain.useCases.property.GetUserHousesUseCase
import com.example.doubles.HouseRepositoryDouble
import domain.entities.House
import domain.entities.Point
import doubles.UserRepositoryDouble
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class GetUserHousesUseCaseTest {

    @Test
    fun `get empty list with user without houses`(){
        val userId = "userId"
        val userRepository = UserRepositoryDouble()
        val houseRepository = HouseRepositoryDouble()
        val useCase = GetUserHousesUseCase(userRepository, houseRepository)

        val result = useCase.execute(userId)

        result.shouldBeEmpty()
    }

    @Test
    fun `get user list houses`(){
        val userId = "userId"
        val userRepository = UserRepositoryDouble()
        val listOfHouses = listOf(
            givenAHouse(ownerId = userId)
        )
        val houseRepository = HouseRepositoryDouble().withListOfProperties(listOfHouses)
        val useCase = GetUserHousesUseCase(userRepository, houseRepository)

        val result = useCase.execute(userId)

        result.shouldBe(listOfHouses)
    }

    @Test
    fun `validate user exist`(){
        val userRepository = UserRepositoryDouble().withUserExist(true)
        val houseRepository = HouseRepositoryDouble()
        val useCase = GetUserHousesUseCase(userRepository, houseRepository)

        val result = useCase.validate("userId")

        result.shouldBeTrue()
    }

    @Test
    fun `not validate user`(){
        val userRepository = UserRepositoryDouble().withUserExist(false)
        val houseRepository = HouseRepositoryDouble()
        val useCase = GetUserHousesUseCase(userRepository, houseRepository)

        val result = useCase.validate("userId")

        result.shouldBeFalse()
    }

    private fun givenAHouse(
        id: String = "",
        ownerId: String = "",
        point: Point = Point(0.0,0.0),
        title: String = "",
        price: Int = 0,
        bedrooms: Int = 0,
        bathrooms: Int = 0,
        area: Double = 0.0,
        imagesUrl: List<String> = listOf(),
        country: String = "",
        department: String = "",
        neighborhood: String = ""
    ) =
        House(
            id,
            ownerId,
            point,
            title,
            price,
            bedrooms,
            bathrooms,
            area,
            imagesUrl,
            "",
            country,
            department,
            neighborhood
        )
}