package domain.useCases.property
import com.example.infra.interfaces.IHouseRepository

class GetHousesUseCase(private val houseRepository: IHouseRepository) {

    fun execute() = houseRepository.getHouses()
}
