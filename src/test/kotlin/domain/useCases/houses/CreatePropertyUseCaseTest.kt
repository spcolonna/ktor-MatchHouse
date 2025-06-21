package com.example.domain.useCases.houses

import delivery.dtos.CreatePropertyDto
import domain.entities.Property
import domain.useCases.property.CreatePropertyUseCase
import domain.builders.PropertyBuilder
import domain.entities.Point
import doubles.CreatePropertyRepositoryDouble
import doubles.IdGeneratorDouble
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class CreatePropertyUseCaseTest {

    @Test
    fun `create a new property`(){
        val id = "id"
        val lon = 0.0
        val lat = 0.0
        val expected = getAProperty(id, Point(lon, lat))
        val dto = CreatePropertyDto(lon, lat, "", 0, 0, 0, 0.0)
        val idGenerator = IdGeneratorDouble(id)
        val repository = CreatePropertyRepositoryDouble()

        val useCase = CreatePropertyUseCase(repository, PropertyBuilder(idGenerator))

        useCase.execute(dto)

        repository.storeProperty.shouldBe(expected)
    }

    @Test
    fun `create another new property`(){
        val id = "anotherId"
        val lon = 0.1
        val lat = 0.1
        val expected = getAProperty(id, Point(lon, lat))
        val dto = CreatePropertyDto(lon, lat, "", 0, 0, 0, 0.0)
        val idGenerator = IdGeneratorDouble(id)
        val repository = CreatePropertyRepositoryDouble()
        val useCase = CreatePropertyUseCase(repository, PropertyBuilder(idGenerator))

        useCase.execute(dto)

        repository.storeProperty.shouldBe(expected)
    }

    @Test
    fun `add new properties to property house`(){
        val id = "id"
        val lon = 0.0
        val lat = 0.0
        val title = "Villa Serrana"
        val price = 12000
        val bedrooms = 2
        val bathrooms = 1
        val area = 23.4
        val expected = getAProperty(id, Point(lon, lat), title, price, bedrooms, bathrooms, area)
        val dto = CreatePropertyDto(lon, lat, title, price, bedrooms, bathrooms, area)
        val idGenerator = IdGeneratorDouble(id)
        val repository = CreatePropertyRepositoryDouble()

        val useCase = CreatePropertyUseCase(repository, PropertyBuilder(idGenerator))

        useCase.execute(dto)

        repository.storeProperty.shouldBe(expected)
    }

    private fun getAProperty(
        id: String,
        point: Point,
        title: String = "",
        price: Int = 0,
        bedrooms: Int = 0,
        bathrooms: Int = 0,
        area: Double = 0.0
    ) =
        Property(
            id,
            point,
            title,
            price,
            bedrooms,
            bathrooms,
            area
        )
}

