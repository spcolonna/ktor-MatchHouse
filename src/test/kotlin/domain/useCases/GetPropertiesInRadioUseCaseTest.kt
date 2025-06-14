package domain.useCases

import domain.entities.Point
import domain.entities.Property
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class GetPropertiesInRadioUseCaseTest {
    @Test
    fun `Get Properties In Radio Use Case Test`(){
        val property = Property("id", Point(0.0,0.0))
        val expected = listOf(property);
        val calculateDistance = CalculateDistanceUseCase();
        val propertiesRepo = PropertiesRepositoryDouble(listOf(property))
        val useCase = GetPropertiesInRadioUseCase(calculateDistance, propertiesRepo);

        val result = useCase.execute(Point(0.0,0.0),0);

        result.shouldBe(expected);
    }
    @Test
    fun `Get Another Properties In Radio Use Case Test`(){
        val property = Property("anotherId", Point(0.0,0.0))
        val property2 = Property("Id", Point(0.0,1.0))
        val expected = listOf(property);
        val calculateDistance = CalculateDistanceUseCase()
        val propertiesRepo = PropertiesRepositoryDouble(listOf(property, property2))
        val useCase = GetPropertiesInRadioUseCase(calculateDistance, propertiesRepo)

        val result = useCase.execute(Point(0.0,0.0),0);
        result.shouldBe(expected);
    }

    @Test
    fun `Get Another Properties In Radio Use Case Test XXXX`(){
        val property = Property("anotherId", Point(0.0,0.0))
        val property2 = Property("Id2", Point(0.0,2.0))
        val property3 = Property("Id3", Point(0.0,1.0))
        val expected = listOf(property, property3);
        val calculateDistance = CalculateDistanceUseCase();
        val propertiesRepo = PropertiesRepositoryDouble(listOf(property, property2, property3))
        val useCase = GetPropertiesInRadioUseCase(calculateDistance, propertiesRepo);

        val result = useCase.execute(Point(0.0,0.0), 150);

        result.shouldBe(expected);
    }
}