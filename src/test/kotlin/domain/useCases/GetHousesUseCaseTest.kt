import com.example.doubles.HouseRepositoryDouble
import domain.entities.Point
import domain.entities.Property
import domain.useCases.property.GetHousesUseCase
import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class GetHousesUseCaseTest {

    @Test
    fun `get empty houses list`(){
        val houseRepository = HouseRepositoryDouble()
        val useCase = GetHousesUseCase(houseRepository)

        val result = useCase.execute()

        result.shouldBeEmpty()
    }

    @Test
    fun `get all houses`(){
        val property = givenAProperty("id", Point(-1.1,1.0))
        val expected = listOf(property)
        val houseRepository = HouseRepositoryDouble().with(expected)
        val useCase = GetHousesUseCase(houseRepository)

        val result = useCase.execute()

        result.shouldBe(expected)
    }

    private fun givenAProperty(
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