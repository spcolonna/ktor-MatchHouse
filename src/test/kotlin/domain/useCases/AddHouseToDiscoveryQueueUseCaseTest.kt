package com.example.domain.useCases

import com.example.doubles.DiscoveryListRepositoryDouble
import com.example.doubles.HouseRepositoryDouble
import domain.entities.House
import domain.entities.Point
import domain.useCases.CalculateDistanceUseCase
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlin.test.Test
import org.mockito.kotlin.*

class AddHouseToDiscoveryQueueUseCaseTest {

    @Test
    fun `NOT add house to discovery list`(){
        val userId = "userId"
        val userPosition = Point(0.0, 1.0)
        val discoveryListRepository = DiscoveryListRepositoryDouble()
        val useCase = AddHouseToDiscoveryQueueUseCase(discoveryListRepository, HouseRepositoryDouble(), CalculateDistanceUseCase())

        useCase.execute(userId, userPosition)

        discoveryListRepository.houseList.shouldBeEmpty()
    }

    @Test
    fun `add house to discovery list`(){
        val userId = "userId"
        val userPosition = Point(0.0, 1.0)
        val houseId = "idHouse"
        val expected = listOf(houseId)
        val listOfHouses = listOf(givenAHouse(id = houseId, point = userPosition))
        val discoveryListRepository = DiscoveryListRepositoryDouble()
        val houseRepository = HouseRepositoryDouble().withListOfProperties(listOfHouses)

        val calculateUseCase = mock<CalculateDistanceUseCase> {
            on { execute(eq(userPosition), eq(userPosition)) } doReturn 0
        }

        val useCase = AddHouseToDiscoveryQueueUseCase(discoveryListRepository, houseRepository, calculateUseCase)

        useCase.execute(userId, userPosition)

        discoveryListRepository.houseList.shouldBe(expected)
        discoveryListRepository.userIdStored.shouldBe(userId)
    }

    @Test
    fun `add only ONE house to discovery list`(){
        val userId = "anotherUserId"
        val userPosition = Point(0.0, 1.0)
        val houseId = "idHouse"
        val expected = listOf(houseId)
        val listOfHouses = listOf(givenAHouse(id = houseId, point = userPosition), givenAHouse(id = "anotherId", point = Point(3.0, 3.0)))
        val discoveryListRepository = DiscoveryListRepositoryDouble()
        val houseRepository = HouseRepositoryDouble().withListOfProperties(listOfHouses)

        val calculateUseCase = mock<CalculateDistanceUseCase> {
            on { execute(any(), any()) } doAnswer { invocation ->
                val p1 = invocation.getArgument<Point>(0)
                val p2 = invocation.getArgument<Point>(1)
                if (p1 == p2) 0 else 999
            }
        }

        val useCase = AddHouseToDiscoveryQueueUseCase(discoveryListRepository, houseRepository, calculateUseCase)

        useCase.execute(userId, userPosition)

        discoveryListRepository.houseList.shouldBe(expected)
        discoveryListRepository.userIdStored.shouldBe(userId)
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