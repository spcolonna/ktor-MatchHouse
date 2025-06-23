package com.example.domain.useCases.houses

import com.example.domain.useCases.property.GetHouseByIdUseCase
import com.example.doubles.HouseRepositoryDouble
import domain.entities.Point
import domain.entities.House
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import kotlin.test.Test


class GetHouseByIdUseCaseTest {

    @Test
    fun `get house by id`(){
        val houseId = "houseId"
        val expected = givenAProperty(houseId)
        val repo = HouseRepositoryDouble().withListOfProperties(listOf(expected)).withHouseExist(true)
        val useCase = GetHouseByIdUseCase(repo)

        val result = useCase.execute(houseId)

        result.shouldBe(expected)
    }

    @Test
    fun `validate house exist`(){
        val houseId = "houseId"
        val repo = HouseRepositoryDouble().withHouseExist(true)
        val useCase = GetHouseByIdUseCase(repo)

        val result = useCase.validate(houseId)

        result.shouldBeTrue()
    }

    @Test
    fun `NOT valid house`(){
        val houseId = "houseId"
        val repo = HouseRepositoryDouble().withHouseExist(false)
        val useCase = GetHouseByIdUseCase(repo)

        val result = useCase.validate(houseId)

        result.shouldBeFalse()
    }

    private fun givenAProperty(
        houseId: String,
        ownerId: String = "",
        point: Point = Point(0.0,0.0),
        title: String = "",
        price: Int = 0,
        bedrooms: Int = 0,
        bathrooms: Int = 0,
        area: Double = 0.0,
        imagesUrl: List<String> = listOf()
    ) = House(houseId,ownerId,point,title, price, bedrooms, bathrooms, area, imagesUrl)
}