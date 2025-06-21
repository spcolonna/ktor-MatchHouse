package com.example.domain.useCases.houses

import domain.entities.Point
import domain.entities.Property
import domain.useCases.CalculateDistanceUseCase
import com.example.domain.useCases.property.GetPropertiesInRadioUseCase
import com.example.doubles.PropertiesRepositoryDouble
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class GetPropertiesInRadioUseCaseTest {
    @Test
    fun `Get Properties In Radio Use Case Test`(){
        val property = getAProperty("id", Point(0.0,0.0))
        val expected = listOf(property);
        val calculateDistance = CalculateDistanceUseCase();
        val propertiesRepo = PropertiesRepositoryDouble(listOf(property))
        val useCase = GetPropertiesInRadioUseCase(calculateDistance, propertiesRepo);

        val result = useCase.execute(Point(0.0,0.0),0);

        result.shouldBe(expected);
    }
    @Test
    fun `Get Another Properties In Radio Use Case Test`(){
        val property = getAProperty("anotherId", Point(0.0,0.0))
        val property2 = getAProperty("Id", Point(0.0,1.0))
        val expected = listOf(property);
        val calculateDistance = CalculateDistanceUseCase()
        val propertiesRepo = PropertiesRepositoryDouble(listOf(property, property2))
        val useCase = GetPropertiesInRadioUseCase(calculateDistance, propertiesRepo)

        val result = useCase.execute(Point(0.0,0.0),0);
        result.shouldBe(expected);
    }

    @Test
    fun `Get Another Properties In Radio Use Case Test XXXX`(){
        val property = getAProperty("anotherId", Point(0.0,0.0))
        val property2 = getAProperty("Id2", Point(0.0,2.0))
        val property3 = getAProperty("Id3", Point(0.0,1.0))
        val expected = listOf(property, property3);
        val calculateDistance = CalculateDistanceUseCase();
        val propertiesRepo = PropertiesRepositoryDouble(listOf(property, property2, property3))
        val useCase = GetPropertiesInRadioUseCase(calculateDistance, propertiesRepo);

        val result = useCase.execute(Point(0.0,0.0), 150);

        result.shouldBe(expected);
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