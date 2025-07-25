package domain.useCases


import domain.entities.Point
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class CalculateDistanceUseCaseTest {
    @Test
    fun `calculate Distance Between Two Points XX`(){
        val point1 = Point(0.0,0.0);
        val point2 = Point(0.0,0.0);
        val useCase = CalculateDistanceUseCase();
        val result = useCase.execute(point1,point2);
        result.shouldBe(0);
    }

    @Test
    fun `calculate Another Distance Between Two Points`(){
        val expected = 111;
        val point1 = Point(0.0,0.0);
        val point2 = Point (0.0, 1.0);
        val useCase = CalculateDistanceUseCase();
        val result = useCase.execute(point1,point2);
        result.shouldBe(expected);
    }

    }



