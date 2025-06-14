package domain

import delivery.dtos.CreatePropertyDto
import domain.entities.Property
import domain.useCases.property.CreatePropertyUseCase
import domain.builders.PropertyBuilder
import domain.entities.Point
import doubles.CreatePropertyRepositoryDouble
import doubles.IdGeneratorDouble
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class CreatePropertyUseCaseUseCaseTest {

    @Test
    fun `create a new property`(){
        val id = "id"
        val lon = 0.0
        val lat = 0.0
        val expected = Property(id, Point(lon, lat))
        val dto = CreatePropertyDto(lon, lat)
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
        val expected = Property(id, Point(lon, lat))
        val dto = CreatePropertyDto(lon, lat)
        val idGenerator = IdGeneratorDouble(id)
        val repository = CreatePropertyRepositoryDouble()
        val useCase = CreatePropertyUseCase(repository, PropertyBuilder(idGenerator))

        useCase.execute(dto)

        repository.storeProperty.shouldBe(expected)
    }
}

