package com.example.domain.useCases.houses

import com.example.delivery.dtos.HouseDto
import delivery.dtos.CreateHouseDto
import domain.entities.House
import domain.useCases.property.CreateHouseUseCase
import domain.builders.PropertyBuilder
import domain.entities.Point
import doubles.CreateHouseRepositoryDouble
import doubles.IdGeneratorDouble
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class CreateHouseUseCaseTest {

    @Test
    fun `create a new property`(){
        val id = "id"
        val ownerId = "ownerId"
        val lon = 0.0
        val lat = 0.0
        val expected = givenAHouse(id, ownerId, Point(lon, lat))
        val dto = givenACreateHouseDto(ownerId, lon, lat)
        val idGenerator = IdGeneratorDouble(id)
        val repository = CreateHouseRepositoryDouble()

        val useCase = CreateHouseUseCase(repository, PropertyBuilder(idGenerator))

        useCase.execute(dto)

        repository.storeProperty.shouldBe(expected)
    }

    @Test
    fun `create another new property`(){
        val id = "anotherId"
        val ownerId = "anotherOwnerId"
        val lon = 0.1
        val lat = 0.1
        val expected = givenAHouse(id, ownerId, Point(lon, lat))
        val dto = givenACreateHouseDto(ownerId, lon, lat)
        val idGenerator = IdGeneratorDouble(id)
        val repository = CreateHouseRepositoryDouble()
        val useCase = CreateHouseUseCase(repository, PropertyBuilder(idGenerator))

        useCase.execute(dto)

        repository.storeProperty.shouldBe(expected)
    }

    @Test
    fun `add new properties to property house`(){
        val id = "id"
        val ownerId = "ownerId"
        val lon = 0.0
        val lat = 0.0
        val title = "Villa Serrana"
        val price = 12000
        val bedrooms = 2
        val bathrooms = 1
        val area = 23.4
        val expected = givenAHouse(id, ownerId, Point(lon, lat), title, price, bedrooms, bathrooms, area)
        val dto = givenACreateHouseDto(ownerId, lon, lat, title, price, bedrooms, bathrooms, area, listOf())
        val idGenerator = IdGeneratorDouble(id)
        val repository = CreateHouseRepositoryDouble()

        val useCase = CreateHouseUseCase(repository, PropertyBuilder(idGenerator))

        useCase.execute(dto)

        repository.storeProperty.shouldBe(expected)
    }

    private fun givenAHouse(
        id: String,
        ownerId: String,
        point: Point,
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

    private fun givenACreateHouseDto(
        ownerId: String,
        lon: Double,
        lat: Double,
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
        CreateHouseDto(
            ownerId,
            lon,
            lat,
            title,
            price,
            bedrooms,
            bathrooms,
            area,
            imagesUrl,
            country,
            department,
            neighborhood
        )
}

